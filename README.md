# smuggler

A solution to the drug smuggler / knapsack algorithm using Clojure and Leiningen.

# Getting Started

I used [homebrew](http://mxcl.github.com/homebrew/) to install all the packages that I needed.

* `brew install clojure`
* `brew install leiningen`

To run the test suite `cd` into smuggler and then run `lein test`.

To run the application `cd` into smuggler and then run `lein repl`.  This will load a clojure REPL in the context of smuggler. Then do the following:

1. Require the core clojure file `(require 'smuggler/core)` (yes that is a single quote... its weird)
2. Run exectute the main method `(smuggler.core/-main)
3. Enter the size of your bag then hit return
4. You should recieve the value of the bag and the dolls as a list of hashes 