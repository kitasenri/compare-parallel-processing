package main

import (
	"fmt"
	"log"
	"strconv"
	"sync"
)

func worker(message string) <-chan string {

	receiver := make(chan string)
	for ii := 0; ii < 20; ii++ {

		go func(i int) {
			mm := fmt.Sprintf("%d %s is finished", i, message)
			receiver <- mm
		}(ii)

	}

	return receiver
}

func main() {

	// loop1
	var wg sync.WaitGroup
	for ii := 0; ii < 10; ii++ {

		wg.Add(1)

		fin := make(chan string)
		go func(ii int, ch chan string) {
			ch <- "Go routine Finished : " + strconv.Itoa(ii)
			wg.Done()
		}(ii, fin)

		log.Println(<-fin)
	}

	wg.Wait()

	// loop2
	var receiver = worker("Worker")
	for ii := 0; ii < 20; ii++ {
		log.Println(<-receiver)
	}

}
