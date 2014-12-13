This is a example Karyon application which utilizes Hystrix for latency controls and monitoring.

It is built on Karyon 2.1.00-RC6, from the original hello world example in the main codebase.

Differences.

* This as a self-contained application, and I plan on using it as a skeleton for further work

* It integrates with Hystrix (probably badly). because the main point in bringing this up is to show to your bosses

* there are two uri's /hello & /hello/to/{xyz}.

* The hello/to command simulates some variability, with 'Alice' taking longer than the maximum (sometimes).


Notes:

* The 'admin' area doesn't seem to be working.