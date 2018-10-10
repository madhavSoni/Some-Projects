import json

# The approach taken here is to first create a nested dictionary using the base_items list
# and then going through each cart item and using the dictionary to determine its base price (if it exists).
# By placing the base_items first into a Dictionary, we are saving ourselves the time of iterating through the entire
# base_item cart repeatedly. We can instead use constant time Dictionary look ups using a more organized data structure.

def calculate(base_item_json, cart_json):

    with open(base_item_json) as f:
        base_items = json.load(f)

    with open(cart_json) as f:
        cart = json.load(f)

    base_item_dictionary = {}

    for base_item in base_items:

        product_type = base_item.get("product-type")

        if product_type not in base_item_dictionary:
            base_item_dictionary[product_type] = []

        keySet = base_item["options"].keys()

        keyValue = {}
        for key in keySet:
            value = base_item["options"][key]
            keyValue[key] = value

        keyValue["base-price"] = base_item.get("base-price")

        base_item_dictionary[product_type].append(keyValue)

    total_price = 0.0

    for cart_item in cart:

        product_name = cart_item.get("product-type")

        keySet = cart_item["options"].keys()

        base_price = -1.0

        if (product_name not in base_item_dictionary):
            return "Error: An unlisted cart item exists."

        for keyValuePair in base_item_dictionary[product_name]:
            count = 0
            for key in keySet:
                if key in keyValuePair:
                    count += 1
                    value = cart_item["options"][key]
                    if value not in keyValuePair[key]:
                        break
                    if value in keyValuePair[key] and count == len(keyValuePair) - 1: # -1 because it contains the base-price
                        base_price = keyValuePair["base-price"]

        markup = cart_item.get("artist-markup")
        quantity = cart_item.get("quantity")

        if (base_price == -1.0):
            return "Error: An unlisted cart item exists."

        total_price = total_price + (base_price + round(base_price * (markup / float(100)))) * quantity

    return total_price
