# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        isStill = 0
        ghostScare = 0
        if currentPos == newPos:
            isStill = -1000000000
        newPosGhost = successorGameState.getGhostPositions()
        newFoodPos = successorGameState.getFood().asList()
        newCapsulePos = successorGameState.getCapsules()
        distanceToGhosts = []
        distanceToFood = []
        distanceToCapsules = []
        for ghost in newPosGhost:
            distanceToGhosts.append(abs(newPos[0] - ghost[0]) + abs(newPos[1] - ghost[1]))
        for food in newFoodPos:
            distanceToFood.append(abs(newPos[0] - food[0]) + abs(newPos[1] - food[1]))
        for cap in newCapsulePos:
            distanceToCapsules.append(abs(newPos[0] - cap[0]) + abs(newPos[1] - cap[1]))
        foodweight = 0
        capsuleweight = 0
        if distanceToFood:
            foodweight = 15/min(distanceToFood)
        if min(distanceToGhosts) < 2:
            ghostScare = -10
        score = successorGameState.getScore() + foodweight + capsuleweight + ghostScare + isStill
        return score

def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action from the current gameState using self.depth
          and self.evaluationFunction.

          Here are some method calls that might be useful when implementing minimax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game

          gameState.isWin():
            Returns whether or not the game state is a winning state

          gameState.isLose():
            Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        numOfAgents = gameState.getNumAgents()

        def value(gameState, calcSoFar, numOfPlies):
            if gameState.isLose() or gameState.isWin() or (self.depth == numOfPlies and calcSoFar%numOfAgents == 0):
                return self.evaluationFunction(gameState)
            if (calcSoFar % numOfAgents == 0):
                return maxValue(gameState, calcSoFar, numOfPlies)
            else:
                return minValue(gameState, calcSoFar, numOfPlies)

        def maxValue(gameState, calcSoFar, numOfPlies):
            v = float('-inf')
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
                v = max(v, value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlies+1))
            return v

        def minValue(gameState, calcSoFar, numOfPlies):
            v = float('inf')
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
                v = min(v, value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlies))
            return v

        v = float('-inf')
        prevV = float('-inf')
        legalActions = gameState.getLegalActions(0)
        takeAction = None
        for action in legalActions:
            v = max(v, value(gameState.generateSuccessor(0, action), 1, 1))
            if prevV < v:
                prevV = v
                takeAction = action
        return takeAction


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        
        numOfAgents = gameState.getNumAgents()

        def value(gameState, calcSoFar, numOfPlies, alpha, beta):
            if gameState.isLose() or gameState.isWin() or (self.depth == numOfPlies and calcSoFar%numOfAgents == 0):
                return self.evaluationFunction(gameState)
            if (calcSoFar % numOfAgents == 0):
                return maxValue(gameState, calcSoFar, numOfPlies, alpha, beta)
            else:
                return minValue(gameState, calcSoFar, numOfPlies, alpha, beta)

        def maxValue(gameState, calcSoFar, numOfPlies, alpha, beta):
            v = float('-inf')
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
              v = max(v, value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlies+1, alpha, beta))
              if v > beta:
                return v
              alpha = max(alpha, v)
            return v

        def minValue(gameState, calcSoFar, numOfPlie, alpha, beta):
            v = float('inf')
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
              v = min(v, value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlie, alpha, beta))
              if v < alpha:
                return v
              beta = min(beta, v)
            return v

        v = float('-inf')
        prevV = float('-inf')
        legalActions = gameState.getLegalActions(0)
        takeAction = None
        alpha = float('-inf')
        beta = float('inf')
        for action in legalActions:
            v = max(v, value(gameState.generateSuccessor(0, action), 1, 1, alpha, beta))
            if prevV < v:
              prevV = v
              takeAction = action
            if v > beta:
              return takeAction
            alpha = max(alpha, v)

        return takeAction






class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction

          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """
        numOfAgents = gameState.getNumAgents()

        def value(gameState, calcSoFar, numOfPlies):
            if gameState.isLose() or gameState.isWin() or (self.depth == numOfPlies and calcSoFar%numOfAgents == 0):
                return self.evaluationFunction(gameState)
            if (calcSoFar % numOfAgents == 0):
                return maxValue(gameState, calcSoFar, numOfPlies)
            else:
                return minValue(gameState, calcSoFar, numOfPlies)

        def maxValue(gameState, calcSoFar, numOfPlies):
            v = float('-inf')
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
                v = max(v, value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlies+1))
            return v

        def minValue(gameState, calcSoFar, numOfPlies):
            v = 0.0
            legalActions = gameState.getLegalActions(calcSoFar%numOfAgents)
            for action in legalActions:
                p = 1.0 / len(legalActions)
                v += p * value(gameState.generateSuccessor(calcSoFar%numOfAgents, action), calcSoFar+1, numOfPlies)
            return v

        v = float('-inf')
        prevV = float('-inf')
        legalActions = gameState.getLegalActions(0)
        takeAction = None
        for action in legalActions:
            v = max(v, value(gameState.generateSuccessor(0, action), 1, 1))
            if prevV < v:
                prevV = v
                takeAction = action
        return takeAction

def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).

      DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"

    if currentGameState.isWin():
      return float('inf')
    if currentGameState.isLose():
      return float('-inf')

    for successorGameState in currentGameState.getLegalActions(currentGameState.g):

      newPos = successorGameState.getPacmanPosition()
      newFood = successorGameState.getFood()
      newGhostStates = successorGameState.getGhostStates()
      newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
      isStill = 0
      ghostScare = 0
      if currentPos == newPos:
        isStill = -1000000000
      newPosGhost = successorGameState.getGhostPositions()
      newFoodPos = successorGameState.getFood().asList()
      newCapsulePos = successorGameState.getCapsules()
      distanceToGhosts = []
      distanceToFood = []
      distanceToCapsules = []
      for ghost in newPosGhost:
        distanceToGhosts.append(abs(newPos[0] - ghost[0]) + abs(newPos[1] - ghost[1]))
      for food in newFoodPos:
        distanceToFood.append(abs(newPos[0] - food[0]) + abs(newPos[1] - food[1]))
      for cap in newCapsulePos:
        distanceToCapsules.append(abs(newPos[0] - cap[0]) + abs(newPos[1] - cap[1]))
      foodweight = 0
      capsuleweight = 0
      if distanceToFood:
        foodweight = 15/min(distanceToFood)
      if min(distanceToGhosts) < 2:
        ghostScare = -10
      score = max(score, successorGameState.getScore() + foodweight + capsuleweight + ghostScare + isStill)
    return score

# Abbreviation
better = betterEvaluationFunction

