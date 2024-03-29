> ## 싱글톤 패턴 (생성)

<details>
    <summary>object</summary>

- java의 static과 kotlin의 object은 동일하게 보이지만 다름.
  - static은 클래스 로더가 클래스를 읽을 때 안에 static이 있다면 메모리 영역에 적재하는 것 뿐임. 새로운 객체를 생성할 수 있음.
  - object는 인스턴스 객체를 단 1개 만들어줌. 새로운 객체를 생성할 수 없음. 이때 만들어진 객체명은 클래스명과 동일함. 
- 실제 사용될 때 초기화 됨.
- 생성자가 없는 클래스만 사용 가능. 
  - 생성자가 없으므로 파라미터를 전달하려면 set으로 설정하는 수밖에 없음.
- 내부 변수가 여러개 일 때, 하나의 변수에 접근만 해도 나머지 하나의 변수도 초기화 된다.
  - 이를 막고 싶다면 변수에 by lazy 사용. (특이한 경우가 아니라면 사용 안할 듯함.)
- 클래스명.(함수/필드)로 호출 가능.
- 기본적으로 스레드 안전.

  ```kotlin
  package singleton
    
  object ObjectSingleton {
    val firstValue = "first"
    val secoundValue by lazy {"lazy"}
  }
  ```

</details>

<details>
    <summary>companion object</summary>

- 해당 클래스가 로드될 때 초기화 됨.
- companion object가 적용된 내부만 싱글톤 객체가 됨. 즉 외부 클래스는 싱글톤 아님.
  - 클래스 수준의 정적 멤버가 필요할 때 사용할 수 있음.
  - 생성자를 만들 수 있어 파라미터를 전달할 수 있음.
- companion object 내에 선언된 속성과 함수는 클래스명/객체명.(함수/필드) 호출 가능.

  ```kotlin
  package singleton
  
  class CompanionObjectSingletone private constructor() {
  
  //    Lazy Initialization
  //    companion object {
  //        private var instance: CompanionObjectSingletone? = null;
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance ?: CompanionObjectSingletone().also {
  //                    instance = it
  //            }
  //        }
  //    }
  
  //    Eager Initialization
  //    companion object {
  //        private var instance: CompanionObjectSingletone =  CompanionObjectSingletone();
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance
  //        }
  //    }
  
  //    double checked locking
  //    companion object {
  //        @Volatile private var instance: CompanionObjectSingletone? = null;
  //
  //        fun getInstance(): CompanionObjectSingletone {
  //            return instance ?: synchronized(this) {
  //                instance ?: CompanionObjectSingletone().also {
  //                    instance = it
  //                }
  //            }
  //        }
  //    }
  
  //    Lazy Holder
  //    inner 키워드를 사용하지 않으면 static 내부 클래스(Inner 클래스) 로 되고
  //    inner 키워드를 사용해야 non-static 내부 클래스(Nested 클래스) 가 된다.
  //    private class LazyHolderInner{
  //        companion object{
  //            val companionObjectSingletone : CompanionObjectSingletone = CompanionObjectSingletone()
  //        }
  //    }
  //
  //    companion object {
  //        fun getInstance(): CompanionObjectSingletone {
  //            return LazyHolderInner.companionObjectSingletone
  //        }
  //    }
  
      // kotlin singleton 완벽한 방법. 
  //    lazy 이용하여 스레드 안전 보장.
  //    생성자도 만들 수 있어 파라미터도 받을 수 있음.
      companion object {
          private val instance: CompanionObjectSingletone by lazy { CompanionObjectSingletone() }
  
          fun getInstance(): CompanionObjectSingletone {
              return instance
          }
      }
  
  }
  ```
</details>

<br/>
<br/>

> ## 팩토리 메소드 (생성)

<details>
  <summary>객체</summary>

- 생성 하려는 객체들

```kotlin
package factoryMethod

open class Drink {
}
```

```kotlin
package factoryMethod

class Coffee : Drink(){
}
```

```kotlin
package factoryMethod

class Tea : Drink() {
}
```
</details>

<details>
  <summary>Factory 추상화</summary>

- factory 인터페이스
- 객체 생성 메소드를 가지고 있음.

  ```kotlin
  package factoryMethod
  
  fun interface DrinkFactory {
      fun makeDrink() : Drink
  }
  ```
</details>

<details>
  <summary>Factory 구현화</summary>

- 실제 객체 생성 기능을 구현한 서브 클래스.

  ```kotlin
  package factoryMethod
  
  class DrinkFactoryImpl : DrinkFactory {
      override fun makeDrink(): Drink {
          println("make Drink")
          return Drink()
      }
  }
  ```

  ```kotlin
  package factoryMethod
  
  class CoffeeFactory : DrinkFactory {
      override fun makeDrink(): Drink {
          println("make Coffee")
          return Coffee()
      }
  }
  ```

  ```kotlin
  package factoryMethod
  
  class TeaFactoryImpl : DrinkFactory{
      override fun makeDrink(): Drink {
          println("make Tea")
          return Tea()
      }
  }
  ```
</details>

<br/>
<br/>

> ## 추상 팩토리 (생성)

<details>
  <summary>객체</summary>

- 객체 : 버거, 음료수
- 객체 집합 : 버거 셋트

  ```kotlin
  package abstractFactory
  
  class BurgerKingBurger:Burger {
      init {
          println("make BurgerKingBurger")
      }
  }
  ```
  
  ```kotlin
  package abstractFactory
  
  class BurgerKingDrink:Drink {
      init {
          println("make BurgerKingDrink")
      }
  }
  ```
  ```kotlin
  package abstractFactory
  
  class MacdonaldBurger() :Burger {
      init {
          println("make MacdonaldBurger")
      }
  }
  ```
  ```kotlin
  package abstractFactory
  
  class MacdonaldDrink:Drink {
      init {
          println("make MacdonaldDrink")
      }
  }
  ```
  ```kotlin
  package abstractFactory
  
  data class BurgerSet(
      val burger:Burger,
      val drink: Drink
  )
  ```

</details> 

<details>
  <summary>Factory 추상화</summary>

- 객체 집합 생성 메소드를 가진 팩토리.

  ```kotlin
  package abstractFactory
  
  interface BurgerSetFactory {
      fun makeBurgerSet(type: String):BurgerSet?
  }
  ```

</details> 

<details>
  <summary>Factory 구현화</summary>

- 실제 객체를 생성하는 팩토리

  ```kotlin
  package abstractFactory
  
  class BurgerSetFactoryImpl:BurgerSetFactory {
      override fun makeBurgerSet(type: String): BurgerSet?{
          var burgerSet:BurgerSet? = null
  
          when(type){
              "Macdonald" -> burgerSet = BurgerSet(MacdonaldBurger(), MacdonaldDrink())
              "BurgerKing" -> burgerSet = BurgerSet(BurgerKingBurger(), BurgerKingDrink())
          }
  
          return burgerSet
      }
  }
  ```
</details> 

<br/>
<br/>

> ## 정적 팩토리 메소드 (생성)

<details>
  <summary>객체</summary>

- companion object 메소드 생성.

  ```kotlin
  package staticFactoryMethod
  
  class Drink {
      companion object{
          fun from():Drink{
              return Drink()
          }
  
          fun of(): Drink{
              return Drink()
          }
  
          fun valueOf():Drink{
              return Drink()
          }
  
          fun getInstance(): Drink{
              return Drink()
          }
          
          fun newInstance(): Drink{
              return Drink()
          }
          
          fun getString():String{
              return "Drink"
          }
          
          fun newString():String{
              return "Drink"
          }
      }
  }
  ```
</details>

<br/>
<br/>

> ## 이넘 팩토리 메소드 (생성)
<details>
  <summary>객체</summary>

- Food 상속 받은 음료수, 햄버거

  ```kotlin
  package enumFactoryMethod
  
  interface Food {
  }
  ```
  
  ```kotlin
  package enumFactoryMethod
  
  class Drink: Food {
      init {
          println("make Drink")
      }
  }
  ```
  
  ```kotlin
  package enumFactoryMethod
  
  class Hamburger:Food {
      init{
          println("make Hamburger")
      }
  }
  ```

</details>

<details>
  <summary>Factory Enum</summary>

- 음식 객체를 생성하는 팩토리 이넘.
- 추상 메소드를 만들고 모든 상수가 해당 메소드를 구현할 수 있도록 하면 됨.

```kotlin
package enumFactoryMethod

enum class FoodFactory(
  val foodName: String
) {
  DRINK("음료수"){
    override fun createFood(): Food {
      return Drink()
    }
  },
  HAMBURGER("햄버거"){
    override fun createFood(): Food {
      return Hamburger()
    }
  };
  abstract fun createFood(): Food
}
```

</details>

<br/>
<br/>

> ## 다이나믹 팩토리 (생성)

<details>
  <summary>Factory Dynamic</summary>

- reflection API를 이용하였음.

```kotlin
package dynamicFactory

import enumFactoryMethod.Drink
import enumFactoryMethod.Food
import enumFactoryMethod.Hamburger
import java.lang.RuntimeException

object DynamicFactory {

    // out을 통해 하위 객체도 저장될 수 있도록 한다.
    private val registerTypes: MutableMap<String, Class<out Food>> = HashMap();

    // 기본 클래스 타입 저장
    init {
        registerTypes["Hamburger"] = Hamburger::class.java
        registerTypes["Drink"] = Drink::class.java
    }

    // 실행 도중 추가하고 싶어진 경우
    fun setRegisterType(type: String, cls: Class<out Food>){
        registerTypes[type] = cls
    }

    // 클래스 확인 후 리턴
    private fun getFoodClass(type: String): Class<out Food> {
        return registerTypes[type] ?: throw RuntimeException("해당 음식 없음")
    }

    // 클래스 생성자를 이용하여 새로운 객체를 만들고 Food로 형변환
    fun createFood(type: String): Food {
        return getFoodClass(type).getDeclaredConstructor().newInstance() as Food
    }
}
```
</details>

<br/>
<br/>

> ## 빌더 (생성)

<details>
  <summary>객체</summary>

- 코틀린은 기본적으로 객체 생성 시 필드명을 매핑하여 순서를 마음대로 정할 수 있기 때문에 가독성 측면에선 큰 도움이 되지않음.
- 코틀린에서 빌드 패턴을 쓸 이유는 단일 책임 원칙을 지키기 위함 또는 생성자 접근을 막기 위함일 것이라 생각 됨.

  ```kotlin
  package builder
  
  class Drink(
      val name: String,
      val size: String,
      val price: String
  ){
      override fun toString(): String {
          return "Drink(name='$name', size='$size', price='$price')"
      }
  }
  ```
</details>

<details>
  <summary>Builder</summary>

- 해당 방법은 빌더 클래스를 따로 생성해야 하며 기존 객체의 생성자를 private로 만들 수 없음.
- 객체 생성 지연 및 생성 기능 분리가 주 목적

  ```kotlin
  package builder
  
  class DrinkBuilder (
      private var name: String = "",
      private var size: String = "",
      private var price: String = ""
  ) {
      fun name(name: String): DrinkBuilder {
          this.name = name
          return this
      }
      fun size(size: String): DrinkBuilder {
          this.size = size
          return this
      }
  
      fun price(price: String): DrinkBuilder {
          this.price = price
          return this
      }
  
      fun build(): Drink{
          return Drink(name, size, price)
      }
  }
  ```
</details>

<details>
  <summary>중첩 클래스 Builder</summary>

- 객체 안에 중첩 클래스를 만듬.
- 객체의 생성자를 private로 만들 수 있음.

  ```kotlin
  package builder
  
  class Hamburger private constructor(
      val name: String,
      val size: String,
      val price: String
  ) {
      // 중첩 클래스로 정적 이너 클래스와 비슷한 개념이다.
      class Builder(
          private var name: String = "",
          private var size: String = "",
          private var price: String = "",
          ) {
          fun name(name: String): Builder {
              this.name = name
              return this
          }
  
          fun size(size: String): Builder {
              this.size = size
              return this
          }
  
          fun price(price: String): Builder {
              this.price = price
              return this
          }
  
          fun build(): Hamburger{
              return Hamburger(name, size, price)
          }
      }
  
      override fun toString(): String {
          return "Hamburger(name='$name', size='$size', price='$price')"
      }
  
  
  }
  ```
</details>


<br/>
<br/>

> ## 프로토타입 (생성)

<details>
  <summary>객체</summary>

- data class에는 기본적으로 copy 메소드가 있음. 다만, 얕은 복사이므로 깊은 복사를 하려면 사용자 정의가 필요함.
- 추가적으로 컬렉션에서는 깊은 복사를 해주는 메소드가 있으니 필요 시 찾아서 사용할 것.

```kotlin
package prototype

data class Drink(val list: List<Int>) {

    fun copy(): Drink{
        val copyList = list.toMutableList()
        return Drink(copyList)
    }
    
    override fun toString(): String {
        val hashCode = System.identityHashCode(list)
        return "Drink(list=$list, $hashCode)"
    }
}
```
</details>

 <br/>
 <br/>

> ## 어댑터 (구조)

<details>
  <summary>객체</summary>

- 기본 차 클래스, 날개 인터페이스

  ```kotlin
  package structural.adapter
  
  open class Car {
      fun start(){
          println("시동 ON")
      }
      fun end(){
          println("시동 OFF")
      }
  }
  ```
  ```kotlin
  package structural.adapter
  
  interface Wing {
      fun fly();
  
  }
  ```
  
</details>

<details>
  <summary>합성</summary>

- 멤버 변수로 차 클래스를 가짐.

  ```kotlin
  package structural.adapter
  
  class FlyCar1(private val car: Car): Wing {
      override fun fly() {
              println("날기")
      }
  
      fun start(){
          car.start()
      }
  
      fun end(){
          car.end()
      }
  }
  ```

</details>

<details>
  <summary>상속</summary>

- 차 클래스와 인터페이스를 둘 다 상속 받음.

  ```kotlin
  package structural.adapter
  
  class FlyCar2():Car(), Wing {
      override fun fly() {
              println("날기")
      }
  }
  ```

</details>


<br/>
<br/>


> ## 브릿지 (구조)

<details>
  <summary>색</summary>

- 자바와 동일.

  ```kotlin
  package structural.bridge
  
  interface Color {
      fun getColor()
  }
  ```
  
  ```kotlin
  package structural.bridge
  
  class Red: Color {
      override fun getColor() {
          println("Red")
      }
  }
  ```

  ```kotlin
  package structural.bridge
  
  class Blue: Color {
      override fun getColor() {
          println("Blue")
      }
  }
  ```

</details>

<details>
  <summary>버튼</summary>

- 자바와 동일.

  ```kotlin
  package structural.bridge
  
  abstract class Button(val color: Color) {
      abstract fun action()
  }
  ```

  ```kotlin
  package structural.bridge
  
  class StartButton(
      color: Color
  ): Button(color) {
      override fun action() {
          println("Start!!")
      }
  }
  ```
  
  ```kotlin
  package structural.bridge
  
  class EndButton(color: Color): Button(color) {
      override fun action() {
          println("End!!!")
      }
  }
  ```

</details>

<br/>
<br/>

> ## 컴포지트 (구조)

<details>
  <summary>인터페이스</summary>

- 자바와 동일.

  ```kotlin
  package structural.composite
  
  interface Item {
      fun getPrice():Int
      fun getName():String
  }
  ```

  ```kotlin
  package structural.composite
  
  interface Box:Item {
      fun getAllPrice(): Int
      fun getItems(): String
      fun addItem(item: Item)
      fun removeItem(item: Item)
  }
  ```

</details>

<details>
  <summary>객체</summary>

  ```kotlin
  package structural.composite
  
  class NormalItem(private val name: String, private val price: Int): Item {
      override fun getPrice():Int {
          return this.price
      }
  
      override fun getName(): String {
          return this.name
      }
  }
  ```

  ```kotlin
  package structural.composite
  
  class NormalBox(private val name: String, private val price: Int) : Box {
      private val list = mutableListOf<Item>()
  
      override fun getAllPrice(): Int = list.sumOf {
          when (it) {
              is Box -> it.getAllPrice() + it.getPrice()
              else -> it.getPrice()
          }
      }
  
      override fun getItems(): String = "$name = { ${list.joinToString(", ") { item ->
          when (item) {
              is Box -> item.getItems()
              else -> item.getName()
          }
      }} }"
  
      override fun addItem(item: Item) {
          list.add(item)
      }
  
      override fun removeItem(item: Item) {
          list.remove(item)
      }
  
      override fun getPrice(): Int = price
  
      override fun getName(): String = name
  }
  
  ```

</details>


<br/>
<br/>

> ## 데코레이터 (구조)

<details>
  <summary>인터페이스</summary>

- 햄버거가 가지고 있어야 하는 기본 기능.

  ```kotlin
  package structural.decorator
  
  interface Hamburger {
      fun getName(): String
  }
  ```
</details>

<details>
  <summary>객체</summary>

- 실제 기본 햄버거 객체.

  ```kotlin
  package structural.decorator
  
  class BasicHamburger(): Hamburger{
      override fun getName(): String {
          return "햄버거"
      }
  }
  ```

</details>


<details>
  <summary>데코레이터</summary>

- 토핑 추가 기능
- 기존 객체에 기능을 더해 새로운 객체로 변환.
- 구현 시 기존 객체를 담는 변수의 타입은 최상위 타입이어야 함. 즉, 햄버거 인터페이스

  ```kotlin
  package structural.decorator
  
  abstract class HamburgerDecorator(private val hamburger: Hamburger): Hamburger {
      override fun getName(): String {
         return hamburger.getName()
      }
  }
  ```

  ```kotlin
  package structural.decorator
  
  class CheeseDecorator(
      val hamburger: Hamburger
  ): HamburgerDecorator(hamburger) {
      override fun getName(): String {
          return "치즈 ${super.getName()}"
      }
  }
  ```
  
  ```kotlin
  package structural.decorator
  
  class BulgogiDecorator(private val hamburger: Hamburger): HamburgerDecorator(hamburger){
      override fun getName(): String {
          return "불고기 ${hamburger.getName()}"
      }
  }
  
  ```

</details>

<br/>
<br/>

> ## 퍼사드 (구조)

<details>
  <summary>객체</summary>

- 객체별 기능 구현

  ```kotlin
  package structural.facade
  
  class Person() {
      fun move(){
          println("움직인다")
      }
  
      fun watch(){
          println("본다")
      }
  }
  ```

  ```kotlin
  package structural.facade
  
  class Tv {
      fun on(){
          println("TV ON")
      }
  }
  ```
  
  ```kotlin
  package structural.facade
  
  class Pizza {
      fun addTopping(){
          println("토핑 추가")
      }
  }
  ```

</details>

<details>
  <summary>퍼사드</summary>

- 서브 객체 기능 호출 집합.

  ```kotlin
  package structural.facade
  
  class Facade {
      fun action(){
          val person = Person()
          val tv = Tv()
          val pizza = Pizza()
  
          person.move()
          pizza.addTopping()
          person.move()
          tv.on()
          person.watch()
  
      }
  }
  ```

</details>

<br/>
<br/>

> ## 플라이웨이트 (구조)

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package structural.flyweight
  
  class Model private constructor(
      val type:String
  ) {
      companion object Factory{
          private val cache = mutableMapOf<String,Model>()
          fun getInstance(type: String): Model{
  
              if(cache.containsKey(type)){
                  print("[기존 나무] ")
              }else{
                  print("[새로운 나무] ")
                  cache[type] = Model(type)
              }
  
              return cache[type]!!
          }
      }
  }
  ```

  ```kotlin
  package structural.flyweight
  
  class Tree private constructor(
      val type: Model,
      val x: Double,
      val y: Double
  ) {
      companion object Factory {
          fun getInstance(type: String): Tree {
              val model = Model.Factory.getInstance(type)
              val x = Math.random() * 10000
              val y = Math.random() * 10000
  
              println("$type 위치: x=${x}, y=${y}")
              return Tree(model, x, y)
          }
      }
  }
  ```

</details>

<br/>
<br/>

> ## 프록시 (구조&행동)

<details>
  <summary>가상 프록시</summary>

- java와 동일.

  ```kotlin
  package structural.proxy
  
  fun interface Image {
      fun showImage()
  }
  ```

  ```kotlin
  package structural.proxy
  
  class HighImage(
      val path:String
  ): Image {
  
      init {
          println("$path 경로 이미지 로딩")
      }
  
      override fun showImage() {
          println("$path 경로 이미지 출력")
      }
  }
  ```

  ```kotlin
  package structural.proxy
  
  class VirtualProxy(val path:String): Image {
  
      init{
          println("$path 경로 프록시 생성")
      }
      
      override fun showImage() {
          val highImage = HighImage(this.path)
          highImage.showImage()    
      }
  }
  ```

</details>

<details>
  <summary>보호 프록시</summary>
  
- java와 동일.

  ```kotlin
  package structural.proxy
  
  class ProtectiveProxy(val path: String, val user: String): Image{
      init{
          println("$path 경로 프록시 생성")
      }
  
      override fun showImage() {
          when(this.user){
              "관리자" -> {
                  println("$user 접근 성공")
                  val highImage = HighImage(this.path)
                  highImage.showImage()
              }
              else -> println("$user 접근 불가")
          }
  
  
      }
  
  }
  ```

</details>

<br/>
<br/>

> ## 책임 연쇄 (행동)

<details>
  <summary>인터페이스</summary>

- 자바와 동일.

  ```kotlin
  package behavioral.chainOfResponsibility
  
  interface Handler {
      fun setNextHandler(handler: Handler)
      fun process(authority: String)
  }
  ```

</details>

<details>
  <summary>추상 클래스</summary>

- java와 동일

  ```kotlin
  package behavioral.chainOfResponsibility
  
  abstract class LoginHandler(): Handler {
      lateinit var handler: Handler
      override fun setNextHandler(handler: Handler) {
          this.handler=handler
      }
  
      override fun process(authority: String) {
          try{
              this.handler.process(authority)
          }catch (exception: Exception){
              println("로그인 실패")
          }
      }
  }
  ```

</details>

<details>
  <summary>객체</summary>

- java와 동일

  ```kotlin
  package behavioral.chainOfResponsibility
  
  class Admin(): LoginHandler() {
      override fun process(authority: String) {
          if("Admin" == authority){
              println("관리자 로그인 성공")
          }else{
              super.process(authority)
          }
      }
  }
  ```

  ```kotlin
  package behavioral.chainOfResponsibility
  
  class User(): LoginHandler() {
      override fun process(authority: String) {
          if("User" == authority){
              println("User 로그인 성공")
          }else{
              super.process(authority)
          }
      }
  }
  ```

</details>

<br/>
<br/>

> ## 커맨드 (행동)

<details>
  <summary>인터페이스</summary>

- java와 동일

  ```kotlin
  package behavioral.command
  
  fun interface Command {
      fun run()
  }
  ```
</details>

<details>
  <summary>객체</summary>

- java와 동일.

  ```kotlin
  package behavioral.command
  
  class Button (private var command: Command? = null){
      fun setCommand(command: Command){
          this.command = command
      }
  
      fun action(){
          this.command?.run()
      }
  }
  ```

  ```kotlin
  package behavioral.command
  
  class HeaterCommand: Command {
      override fun run() {
          println("히터 ON")
      }
  }
  ```

  ```kotlin
  package behavioral.command
  
  class LampCommand: Command {
      override fun run() {
          println("램프 ON")
      }
  }
  ```

</details>

<br/>
<br/>

> ## 인터프리터 (행동)

<details>
  <summary>인터페이스</summary>

- java와 동일.

  ```kotlin
  package behavioral.interpreter
  
  fun interface Expression {
      fun interpret(): Double
  }
  ```

</details>

<details>
  <summary>객체</summary>
  
- Java와 동일.

  ```kotlin
  package behavioral.interpreter
  
  class Number(private val double: Double): Expression {
  
      override fun interpret(): Double {
          return this.double
      }
  }
  ```

  ```kotlin
  package behavioral.interpreter
  
  class Addition(private val leftExpression: Expression, private val rightExpression: Expression): Expression {
  
      override fun interpret(): Double {
          return leftExpression.interpret() + rightExpression.interpret();
      }
  }
  ```

  ```kotlin
  package behavioral.interpreter
  
  class Division(private val leftExpression: Expression, private val rightExpression: Expression): Expression {
  
      override fun interpret(): Double {
          if (rightExpression.interpret() == 0.0) throw ArithmeticException("Division by zero")
          return leftExpression.interpret() / rightExpression.interpret()
      }
  }
  ```

  ```kotlin
  package behavioral.interpreter
  
  class Multiplication(private val leftExpression: Expression, private val rightExpression: Expression): Expression {
      override fun interpret(): Double {
          return leftExpression.interpret() * rightExpression.interpret()
      }
  }
  ```

  ```kotlin
  package behavioral.interpreter
  
  class Subtraction(private val leftExpression: Expression, private val rightExpression: Expression): Expression {
      override fun interpret(): Double {
          return leftExpression.interpret() - rightExpression.interpret()
      }
  }
  ```

</details>

<details>
  <summary>기능 구현</summary>

- Java와 동일.

  ```kotlin
  package behavioral.interpreter
  
  import java.util.*
  
  fun main() {
      println("사칙연산 표현식을 입력하세요")
      val userInput = readln()
  
      val expression = buildExpression(userInput)
  
      try {
          val result = expression.interpret()
          println("결과: $result")
      } catch (e: Exception) {
          println("오류 발생: ${e.message}")
      }
  }
  
  private fun buildExpression(userInput: String): Expression {
      val tokens = userInput.split(" ")
      val expressionStack = Stack<Expression>()
      val operatorStack = Stack<String>()
  
      for (token in tokens) {
          if (isNumeric(token)) {
              expressionStack.push(Number(token.toDouble()))
          } else if ("+-*/".contains(token)) {
              while (operatorStack.isNotEmpty() && hasPrecedence(token, operatorStack.peek())) {
                  val topOperator = operatorStack.pop()
                  val rightOperand = expressionStack.pop()
                  val leftOperand = expressionStack.pop()
                  expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator))
              }
              operatorStack.push(token)
          } else {
              throw IllegalArgumentException("잘못된 표현식입니다: $token")
          }
      }
  
      while (operatorStack.isNotEmpty()) {
          val topOperator = operatorStack.pop()
          val rightOperand = expressionStack.pop()
          val leftOperand = expressionStack.pop()
          expressionStack.push(createOperatorExpression(leftOperand, rightOperand, topOperator))
      }
  
      return if (expressionStack.size == 1) {
          expressionStack.pop()
      } else {
          throw IllegalArgumentException("잘못된 표현식입니다.")
      }
  }
  
  private fun createOperatorExpression(left: Expression, right: Expression, operator: String): Expression {
      return when (operator) {
          "+" -> Addition(left, right)
          "-" -> Subtraction(left, right)
          "*" -> Multiplication(left, right)
          "/" -> Division(left, right)
          else -> throw IllegalArgumentException("지원되지 않는 연산자입니다: $operator")
      }
  }
  
  private fun isNumeric(str: String): Boolean {
      return try {
          str.toDouble()
          true
      } catch (e: NumberFormatException) {
          false
      }
  }
  
  private fun hasPrecedence(op1: String, op2: String): Boolean {
      return (!op1.equals("*") && !op1.equals("/")) || (!op2.equals("+") && !op2.equals("-"))
  }
  ```

</details>

<br/>
<br/>


> ## 반복자 (행동)

<details>
  <summary>Iterator</summary>

- Java와 동일.
- 참고로 next()의 경우 null일 수 있기 때문에 nullable 해주어야 함.
  - 배열을 원하는 사이즈로 초기화 하고 값은 주지 않을 경우 null 이기 때문임. 

  ```kotlin
  package behavioral.iterator
  
  
  interface Iterator {
      fun hasNext(): Boolean
      fun next(): Any?
  }
  ```

  ```kotlin
  package behavioral.iterator
  
  class HamburgerIterator(private val arr: Array<Hamburger?>): Iterator {
      private var index = 0
  
      override fun hasNext(): Boolean {
          return index < arr.size
      }
  
      override fun next(): Any? {
          return arr[index++]
      }
  }
  ```

</details>

<details>
  <summary>Collection</summary>

- Java와 동일.
- 실제 객체를 담을 그릇.

  ```kotlin
  package behavioral.iterator
  
  
  fun interface Collection {
      fun iterator(): Iterator
  }
  ```

  ```kotlin
  package behavioral.iterator
  
  class HamburgerCollection(private val size: Int) : Collection {
      private val arr = arrayOfNulls<Hamburger>(size)
      private var index = 0
  
      fun add(hamburger: Hamburger) {
          if (index < arr.size) {
              arr[index++] = hamburger
          }
      }
  
      override fun iterator(): Iterator {
          return HamburgerIterator(arr)
      }
  }
  ```

</details>

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package behavioral.iterator
  
  class Hamburger(private val price:Int, private val name: String) {
      override fun toString(): String {
          return "Hamburger(price=$price, name='$name')"
      }
  }
  ```

</details>

<br/>
<br/>


> ## 중재자 (행동)

<details>
  <summary>중재자</summary>

- Java와 동일.

  ```kotlin
  package behavioral.mediator
  
  interface Mediactor {
      fun forwardRequest(msg: String)
      fun notice()
  }
  ```

  ```kotlin
  package behavioral.mediator
  
  class ItemMediactor : Mediactor {
  
      private val list = mutableListOf<Adventurer>();
  
      fun addAdventurer(adventurer: Adventurer) {
          list.add(adventurer)
      }
  
      override fun forwardRequest(msg: String) {
          notice()
          list.forEach {
              print("${it.name} 에게 전달 -> ")
              it.receiveRequestToMediactor(msg)
          }
      }
  
      override fun notice() {
          println("[중재인 요청 전달 목록]")
      }
  }
  ```

</details>

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package behavioral.mediator
  
  class Adventurer(val name: String) {
      private var itemMediactor: ItemMediactor? = null
  
      fun setMediactor(itemMediactor: ItemMediactor) {
          this.itemMediactor = itemMediactor
          itemMediactor.addAdventurer(this)
      }
  
      fun sendRequestToMediactor(msg: String) {
          itemMediactor?.forwardRequest(msg)
      }
  
      fun receiveRequestToMediactor(msg: String) {
          println("전달 받은 내용: $msg")
      }
  
  }
  ```

</details>

<br/>
<br/>

> ## 메멘토 (행동)

<details>
  <summary>메멘토</summary>

- Java와 동일.

  ```kotlin
  package behavioral.memento
  
  class Memento(val job: String, val level: Int) {
  }
  ```

</details>

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package behavioral.memento
  
  class Adventurer(
      var job: String,
      var level: Int
  ) {
  
      fun setData(memento: Memento) {
          this.job = memento.job
          this.level = memento.level
      }
  
      fun createMemento(): Memento{
          return Memento(job,level)
      }
  
      override fun toString(): String {
          return "Adventurer(job='$job', level=$level)"
      }
  }
  ```

</details>

<br/>
<br/>

> ## 옵저버 (행동)

<details>
  <summary>옵저버</summary>

- Java와 동일.

  ```kotlin
  package behavioral.obsever
  
  fun interface Observer {
      fun receiveNotice(msg: String);
  }
  ```

  ```kotlin
  package behavioral.obsever
  
  class Adventurer(private val name: String): Observer {
      override fun receiveNotice(msg: String) {
          println("${name}님 메시지가 도작했습니다. 내용: $msg")
      }
  }
  ```

</details>

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package behavioral.obsever
  
  interface Subject {
  
      fun registerObserver(observer: Observer)
      fun removeObserver(observer: Observer)
      fun sendNotice(msg: String)
  }
  ```

  ```kotlin
  package behavioral.obsever
  
  class Store: Subject {
      private val subscribers = mutableListOf<Observer>()
  
      override fun registerObserver(observer: Observer) {
          subscribers.add(observer)
      }
  
      override fun removeObserver(observer: Observer) {
          subscribers.remove(observer)
      }
  
      override fun sendNotice(msg: String) {
          println("[구독자 송신 목록]")
          subscribers.forEach{
              it.receiveNotice(msg)
          }
      }
  }
  ```

</details>

<br/>
<br/>

> ## 상태 (행동)

<details>
  <summary>상태</summary>

- Java와 동일.

  ```kotlin
  package behavioral.state
  
  interface State {
      fun powerButtonPush(laptop: Laptop)
      fun typeButtonPush()
  }
  ```

  ```kotlin
  package behavioral.state
  
  object OffState : State {
      override fun powerButtonPush(laptop: Laptop) {
          println("OFF -> ON")
          laptop.setState(OnState)
      }
  
      override fun typeButtonPush() {
          println("무반응")
      }
  
      override fun toString(): String {
          return "현재 상태 OFF"
      }
  
  
  }
  ```

  ```kotlin
  package behavioral.state
  
  object OnState : State {
      override fun powerButtonPush(laptop: Laptop) {
          println("ON -> OFF")
          laptop.setState(OffState)
      }
  
      override fun typeButtonPush() {
          println("타자 입력")
      }
  
      override fun toString(): String {
          return "현재 상태 ON"
      }
  
  
  }
  ```

</details>

<details>
  <summary>객체</summary>

- Java와 동일.

  ```kotlin
  package behavioral.state
  
  class Laptop {
      private var state: State = OffState
  
      fun setState(state: State) {
          this.state = state
      }
  
      fun powerButtonPush() {
          state.powerButtonPush(this)
      }
  
      fun typeButtonPush() {
          state.typeButtonPush()
      }
  
      fun currentState() {
          println(state.toString())
      }
  
  
  }
  ```

</details>

<br/>
<br/>

> ## 전략 (행동)

<details>
  <summary>전략</summary>

- Java와 동일.

  ```kotlin
  package behavioral.strategy
  
  fun interface Skill {
      fun active()
  }
  ```

  ```kotlin
  package behavioral.strategy
  
  class Magic: Skill {
      override fun active() {
          println("마법 스킬 발동")
      }
  }
  ```

  ```kotlin
  package behavioral.strategy
  
  class Fence: Skill {
      override fun active() {
          println("검술 스킬 발동")
      }
  }
  ```

</details>


<details>
  <summary>객체</summary>

- Java 와 동일.

  ```kotlin
  package behavioral.strategy
  
  class Adventurer(var skill: Skill) {
      fun useSkill(){
          skill.active()
      }
  }
  ```

</details>


<br/>
<br/>

> ## 템플릿 메소드 (행동)

<details>
  <summary>템플릿 메소드</summary>

- Java 동일.

```kotlin
package behavioral.template

abstract class Adventurer {
    fun attack(){
        println("공격 전 준비 자세")
        action()
        println("공격 시작")
    }

    protected abstract fun action()
}
```

</details>

<details>
  <summary>객체</summary>

- Java 동일.

  ```kotlin
  package behavioral.template
  
  class Warrior:Adventurer() {
  
    override fun action() {
      println("힘을 모은다.")
    }
  
  }
  ```

  ```kotlin
  package behavioral.template
  
  class Wizard: Adventurer() {
      override fun action() {
          println("마나를 모은다.")
      }
  }
  ```

</details>

<br/>
<br/>

> ## 비지터 (행동)

<details>
  <summary>방문 대상</summary>

- Java 동일.

  ```kotlin
  package behavioral.visitor
  
  fun interface Element {
      fun accept(visitor: Visitor)
  }
  ```

  ```kotlin
  package behavioral.visitor
  
  class Company: Element{
      override fun accept(visitor: Visitor) {
          println("회사 : 사람이 방문했습니다.")
          visitor.visitor(this)
      }
  }
  ```

  ```kotlin
  package behavioral.visitor
  
  class School:Element {
      override fun accept(visitor: Visitor) {
          println("회사 : 방문자가 왔습니다.")
          visitor.visitor(this)
      }
  }
  ```

</details>

<details>
  <summary>방문자</summary>

- Java 동일.

  ```kotlin
  package behavioral.visitor
  
  interface Visitor {
      fun visitor(company: Company)
      fun visitor(school: School)
  
  }
  ```

  ```kotlin
  package behavioral.visitor
  
  class Person:Visitor {
      override fun visitor(company: Company) {
          println("사람 : 일을 합니다.")
      }
  
      override fun visitor(school: School) {
          println("사람 : 공부를 합니다.")
      }
  }
  ```

</details>