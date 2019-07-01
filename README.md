# game-of-life-clj

[![CircleCI](https://circleci.com/gh/idleherb/game-of-life-clj.svg?style=svg)](https://circleci.com/gh/idleherb/game-of-life-clj)

A basic version of Conway's Game of Life written in Clojure.

## Installation

Clone from <https://github.com/idleherb/game-of-life-clj>.

## Usage

### Dev with Hot Reloading

    $1 lein run
    $2 lein figwheel

In your browser, open <http://0.0.0.0:3449>.

### Prod

    $ lein uberjar
    $ java -jar game-of-life-clj-standalone.jar host port

In your browser, open <http://0.0.0.0:8080>.

## License

See UNLICENSE.
