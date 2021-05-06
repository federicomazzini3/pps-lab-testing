package testLab.ex3

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import testLab._

@RunWith(classOf[JUnitRunner])
class CartTestFlatSpec extends FunSpec {

  describe("A Cart") {
    describe("when empty") {
      it("should have size 0") {
        assert(new BasicCart().size == 0)
      }

      it("should have total cost 0") {
        assert(new BasicCart().totalCost == 0)
      }

      it("should have no content") {
        assert(new BasicCart().content.size == 0)
      }
    }
  }

  describe("After insert one element in a cart") {
    describe("total cost") {
      it("should be the cost of the item"){
        val cart = new BasicCart()
        val price = Price(40)
        val item = Item(Product("Jeans"), ItemDetails(1, price))
        cart.add(item)
        assert(cart.totalCost == price.value)
      }
    }
  }

  describe("Two product added to the cart"){
    describe("total cost"){
      it("should be their price sum"){
        val cart = new BasicCart()
        val price1 = Price(10)
        val price2 = Price(5)
        val item1 = Item(Product("T-Shirt"), ItemDetails(1, price1))
        val item2 = Item(Product("Socks"), ItemDetails(1, price2))
        cart.add(item1)
        cart.add(item2)
        assert(cart.content.size == 2)
        assert(cart.totalCost == price1.value + price2.value)
      }
    }
  }
}
