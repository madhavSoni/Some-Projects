import unittest
import PriceCalculator


class TestOriginal(unittest.TestCase):

    # contains non-existant value "huge"
    def test_error(self):
        self.assertEqual(PriceCalculator.calculate("base_items_test.json", "cart_error.json"), "Error: An unlisted cart item exists.")

    # contains non-existant value product-type "jaguar"
    def test_error_2(self):
        self.assertEqual(PriceCalculator.calculate("base_items_test.json", "cart_error_2.json"), "Error: An unlisted cart item exists.")

    # missing an option
    def test_error_3(self):
        self.assertEqual(PriceCalculator.calculate("base_items_test.json", "cart_error_3.json"), "Error: An unlisted cart item exists.")

    def test_error_4(self):
        self.assertEqual(PriceCalculator.calculate("base_items_test.json", "cart_extra_field.json"), 24320.0)

    def test_error_5(self):
        self.assertEqual(PriceCalculator.calculate("base_items_test.json", "cart_normal.json"), 22100.0)


if __name__ == '__main__':
    unittest.main()