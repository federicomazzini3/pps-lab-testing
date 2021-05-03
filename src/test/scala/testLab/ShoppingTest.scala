package testLab

import org.junit.Assert.{assertFalse}
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CartTestFunSuite extends FunSuite with Matchers {
  test("A new cart should have size zero"){
    assert(new BasicCart().size == 0)
  }

  test("A new cart should have totalCost zero"){
    assert(new BasicCart().totalCost == 0)
  }

  test("A new cart should have no content"){
    assert(new BasicCart().content.size == 0)
  }

  test("after insert one element, total cost should be the cost of the element"){
    val cart = new BasicCart()
    val price = Price(40)
    val item = Item(Product("Jeans"), ItemDetails(1, price))
    cart.add(item)
    assert(cart.totalCost == price.value)
  }

  test("two product added to cart, total cost should be their price sum"){
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

@RunWith(classOf[JUnitRunner])
class CatalogTestFunSuite extends FunSuite with Matchers {

  val (p1Product, p2Product) = (Product("T-shirt"), Product("Trouser"))
  val (p1Price, p2Price) = (Price(10), Price(30))

  val catalog = new BasicCatalog(Map[Product, Price](
    p1Product -> p1Price,
    p2Product -> p2Price
  ))

  test("Create catalog with two products"){
    assert(catalog.products.size == 2)
  }

  test("Price for a product"){
    assert(catalog.priceFor(p1Product) == p1Price)
    assertFalse(catalog.priceFor(p2Product) == p1Price)
  }

  test("Price for a product with quantity > 1"){
    val qty = 5
    assertFalse(catalog.priceFor(p1Product, qty).value == p1Price.value)
    assert(catalog.priceFor(p1Product, qty).value == p1Price.value * qty)
    assert(catalog.priceFor(p2Product, qty).value == p2Price.value * qty)
  }
}

@RunWith(classOf[JUnitRunner])
class WareHouseFunSuite extends FunSuite with Matchers {

    test("Add to warehouse a product: method get should return it when asked"){
      val warehouse = new BasicWarehouse
      val p1 = Product("Shoes")
      val qty = 10
      warehouse.supply(p1, qty)

      assert(warehouse.get(p1, qty) == (p1,qty))
    }

    test("Warehouse shouldn't return a quantity of the product asked if there's isn't enough"){
      val warehouse = new BasicWarehouse
      val p1 = Product("Shoes")
      val qty = 10
      warehouse.supply(p1, qty)

      assertFalse(warehouse.get(p1,qty + 1) == (p1, qty + 1))
    }

  test("Warehouse should return the max quantity of the product asked if asked quantity is > than available quantity"){
    val warehouse = new BasicWarehouse
    val p1 = Product("Shoes")
    val qty = 10
    warehouse.supply(p1, qty)

    assert(warehouse.get(p1,qty + 100) == (p1, qty))
  }

  test("Empty warehouse"){
    val warehouse = new BasicWarehouse

    assert(warehouse.get(Product("Fake"), 10) == (Product("Fake"), 0))
  }
}

