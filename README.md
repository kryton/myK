This is a example Karyon application which utilizes Hystrix for latency controls and monitoring.

It is built on Karyon 2.1.00-RC6, from the original hello world example in the main codebase.

Differences.

* This as a self-contained application, and I plan on using it as a skeleton for further work

* It integrates with Hystrix (probably badly). 

* there are two uri's /hello & /hello/to/{xyz}.

* The hello/to command simulates some variability, with 'Alice' taking longer than the maximum (sometimes), and 'Bob' failing all the time.



Notes:

* The 'admin' area doesn't seem to be working.

* you will probably need to point to your own eureka server in eureka-client.properties

* you should be able to run this via 
    > mvn exec:java


What to do:

run 3-4 of these, add turbine to aggregate the performance streams into a single visual, and then start up a couple of loadtests (eg. apachebench), doing requests to Alice, Bob, and yourself to see how the circuit works.
