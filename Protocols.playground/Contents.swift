import UIKit

// Syntax

//protocol Protocol {
//
//}
//protocol Protocol2 {
//
//}
//// Mot class hay struct deu co the dung 1 hay nhieu protocols
//
//class SomeClass: Protocol,Protocol2 {
//
//}
////Property
//protocol Person {
//    var fullName: String {get} // co the khai bao let or var
//    var age: Int {set get}
//
//}
//class Student: Person {
//    let fullName: String = "Nguyen van duc"
//
//    var age: Int = 23
//
//    init(age: Int) {
//        self.age = age
//    }
//}
//
//let student = Student.init(age: 24)
//student.age
//student.fullName

// static

//protocol Person {
//    static var name : String {get set}
//    static func info()
//}

//class Employees: Person {
//
//
//    static var name: String = "Duc"
//    static func info() {
//        print("Name: \(name)")
//    }
//
//}
//Employees.info()
// mutating protocol

//protocol Togglable {
//    mutating func toggle()
//}
//
//enum OnOffSwitch: Togglable {
//    case off, on
//    mutating func toggle() {
//        switch self {
//        case .off:
//            self = .on
//        case .on:
//            self = .off
//        }
//    }
//}
//var lightSwitch = OnOffSwitch.off
//lightSwitch.toggle()
//// lightSwitch is now equal to .onv

//Init protocol
//protocol SomeProtocol {
//    init(someParameter: Int)
//}
//class SomeClass: SomeProtocol {
//    required init(someParameter: Int) {
//
//    }
//}

//Protocol as Type

//protocol RandomNumberGenerator {
//    func random() -> Double
//}
//class LinearCongruentialGenerator: RandomNumberGenerator {
//    var lastRandom = 42.0
//    let m = 139968.0
//    let a = 3877.0
//    let c = 29573.0
//    func random() -> Double {
//        lastRandom = ((lastRandom * a + c)
//            .truncatingRemainder(dividingBy:m))
//        return lastRandom / m
//    }
//}
//class Dice {
//    let sides: Int
//    let generator: RandomNumberGenerator
//    init(sides: Int, generator: RandomNumberGenerator) {
//        self.sides = sides
//        self.generator = generator
//    }
//    func roll() -> Int {
//        return Int(generator.random() * Double(sides)) + 1
//    }
//}
//var d6 = Dice(sides: 6, generator: LinearCongruentialGenerator())
//for _ in 1...5 {
//    print("Random dice roll is \(d6.roll())")
//}
// Random dice roll is 3
// Random dice roll is 5
// Random dice roll is 4
// Random dice roll is 5
// Random dice roll is 4
