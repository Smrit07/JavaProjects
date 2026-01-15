// Base class (Parent)
class Animal {
    // Overridden method
    public void speak() {
        System.out.println("Animal makes a sound.");
    }

    // Overloaded method (same name, different parameter)
    public void speak(String mood) {
        System.out.println("Animal is " + mood + " and makes a sound.");
    }
}

// Derived class: Dog
class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Dog barks: Woof Woof!");
    }

    // Extra behavior
    public void fetch() {
        System.out.println("Dog fetches the ball!");
    }
}

// Derived class: Cat
class Cat extends Animal {
    @Override
    public void speak() {
        System.out.println("Cat meows: Meow Meow!");
    }

    // Extra behavior
    public void scratch() {
        System.out.println("Cat scratches the sofa!");
    }
}

// Derived class: Bird
class Bird extends Animal {
    @Override
    public void speak() {
        System.out.println("Bird chirps: Tweet Tweet!");
    }

    // Extra behavior
    public void fly() {
        System.out.println("Bird is flying in the sky!");
    }
}

// Demo class
public class AnimalDemo {
    public static void main(String[] args) {
        // Polymorphism: Parent reference, Child object
        Animal a1 = new Dog();
        Animal a2 = new Cat();
        Animal a3 = new Bird();

        // Demonstrating method overriding (runtime polymorphism)
        a1.speak();  // Dog's version
        a2.speak();  // Cat's version
        a3.speak();  // Bird's version

        System.out.println();

        // Demonstrating method overloading
        a1.speak("happy");  // Calls overloaded method in Animal

        System.out.println();

        // Downcasting to access child-specific methods
        if (a1 instanceof Dog) {
            ((Dog)a1).fetch();
        }
        if (a2 instanceof Cat) {
            ((Cat)a2).scratch();
        }
        if (a3 instanceof Bird) {
            ((Bird)a3).fly();
        }
    }
}
