import unittest
import PriceCalculator

class TestOriginal(unittest.TestCase):


    def test_4560(self):
        self.assertEqual(PriceCalculator.calculate("base_items.json", "cart_4560.json"), 4560.0)

    def test_9363(self):
        self.assertEqual(PriceCalculator.calculate("base_items.json", "cart_9363.json"), 9363.0)

    def test_9500(self):
        self.assertEqual(PriceCalculator.calculate("base_items.json", "cart_9500.json"), 9500.0)

    def test_11356(self):
        self.assertEqual(PriceCalculator.calculate("base_items.json", "cart_11356.json"), 11356.0)


if __name__ == '__main__':
    unittest.main()