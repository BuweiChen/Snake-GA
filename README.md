# Snake-GA
Hi! Welcome to my 2020 ISEF Finalist project on training an AI with genetic algorithms to play the Snake game

This project, titled **Implementing Supervised Deep Learning with Feedforward Neural Network Using Genetic Algorithms**, had qualified for and was submitted to Regeneron ISEF 2020, here is a link to the [abstract](https://abstracts.societyforscience.org/Home/FullAbstract?Category=Any%20Category&AllAbstracts=True&FairCountry=Any%20Country&FairState=Any%20State&ProjectId=19336), the content of which I have also included below.

### Abstract

Neural networks and genetic algorithms (GAs) are two techniques for optimization and machine learning, each with its own strengths and weaknesses. This project demonstrated the efficiency and applications of genetic algorithms and a neural network trained with genetic algorithms by implementing a combination of both to solve simulated problems without pre-existing data. A smart dots simulation program was designed and programmed in which the dots found the most efficient way to the goal using GAs. Then, a feedforward neural network (FNN) was designed for playing the game of Snake and was trained by GAs. The algorithms were implemented for each problem in Java. For each generation, the fitness of the fittest individual and the fitness sum of the population were recorded, and graphed in excel. Data was analyzed for trend of growth. The genetic algorithm trained the Snake FNN to reach local minimum for errors over 6000 generations. A popular game strategy in the population emerged and became more obvious towards the end of training. The fitness sums of generations of both the smart dots simulation and the Snake FNN reflected logistic growth. The smart dots simulation consistently found the solution within the first 15 generations and optimized it in the next 100 generations. This project demonstrated that a combination of GAs and neural networks could be used to effectively solve complex pattern classification problems without pre-existing data. This method could be applied to various fields, such as drug research, where it can be used to find the best-performing drugs from an array of possible candidates.

### TheDotGameGA

I implemented TheDotGameGA as a proof of concept of/practice for implementing genetic algorithms. The dots have randomly-initialized brains that passes on through the generations through my implementation of natural selection and mutations. Their goal is to get as close to the final red dot as possible or to get there in as little steps as possible. Their fitness is calculated through how well they each accomplish their goal and the most successful ones have the highest chance of passing down their genes, or brains.

Also included is a class that helps the user create obstacles that disable any dot that comes in contact with them from moving. This served as an additional challenge for the dots to adapt to.

