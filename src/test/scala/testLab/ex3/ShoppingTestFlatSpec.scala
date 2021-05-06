package testLab.ex3

import org.junit.Assert.assertFalse
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import testLab._

@RunWith(classOf[JUnitRunner])
class CartTestFlatSpec extends FlatSpec {
  "A new cart" should "have size 0" in {
    assert(new BasicCart().size == 0)
  }

  "A new cart" should "have total cost 0" in {
    assert(new BasicCart().totalCost == 0)
  }

  "A new cart" should "have no content" in {
    assert(new BasicCart().content.size == 0)
  }

  "After insert one elemet to cart, total cost" should "be the cost of the item" in {
    val cart = new BasicCart()
    val price = Price(40)
    val item = Item(Product("Jeans"), ItemDetails(1, price))
    cart.add(item)
    assert(cart.totalCost == price.value)
  }

  "Two product added to the cart, total cost" should "be thei price sum" in {
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

  @RunWith(classOf[JUnitRunner])
  class CatalogTestFlatSpec extends FlatSpec {

    val (p1Product, p2Product) = (Product("T-shirt"), Product("Trouser"))
    val (p1Price, p2Price) = (Price(10), Price(30))

    val catalog = new BasicCatalog(Map[Product, Price](
      p1Product -> p1Price,
      p2Product -> p2Price
    ))

    "A new Catalog with 2 product added" should "have 2 product" in{
      assert(catalog.products.size == 2)
    }

    "catalog in test" should "return the correct prices for its product" in {
      assert(catalog.priceFor(p1Product) == p1Price)
      assertFalse(catalog.priceFor(p2Product) == p1Price)
      assert(catalog.priceFor(p2Product) == p2Price)
    }

    "request a product with quantity n" should "return price product * n" in {
      val qty = 5
      assertFalse(catalog.priceFor(p1Product, qty).value == p1Price.value)
      assert(catalog.priceFor(p1Product, qty).value == p1Price.value * qty)
      assert(catalog.priceFor(p2Product, qty).value == p2Price.value * qty)
    }
  }

  @RunWith(classOf[JUnitRunner])
  class WareHouseFunSuite extends FlatSpec {

    "Add to warehouse a product: method get" should "return it when asked" in {
      val warehouse = new BasicWarehouse
      val p1 = Product("Shoes")
      val qty = 10
      warehouse.supply(p1, qty)
      assert(warehouse.get(p1, qty) == (p1, qty))
    }

    "Warehouse" should "return the max quantity of the product if the asked product quantity is > than the available quantity" in {
      val warehouse = new BasicWarehouse
      val p1 = Product("Shoes")
      val qty = 10
      warehouse.supply(p1, qty)
      assert(warehouse.get(p1, qty + 100) == (p1, qty))
    }

    "Empty warehouse" should "return the product asked with 0 quantity" in {
      val warehouse = new BasicWarehouse
      assert(warehouse.get(Product("Fake"), 10) == (Product("Fake"), 0))
    }
  }
}
