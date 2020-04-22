package main

import (
	"fmt"
	"sync"
	"time"
	"math/rand"
)

func initRandom(arr []int, size int) {
	source := rand.NewSource(time.Now().UnixNano())
	r1 := rand.New(source)
	
	for i := 0; i < size * size; i++ {
		arr[i] = r1.Intn(10) + 1
	}
}

func transpose(arr []int, size int) {
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			tmp := arr[i * size + j]
			arr[i * size + j] = arr[j * size + i]
			arr[j * size + i] = tmp
		}
	}
}

func thread(A []int, B []int, C []int, size int, jobs <-chan int, wg *sync.WaitGroup) {
	defer wg.Done()

	for i := range jobs {
		for j := 0; j < size; j++ {
			tmp := 0

			for k := 0; k < size; k++ {
				tmp += A[i * size + k] * B[j * size + k]
			}

			C[i * size + j] = tmp
		}
	}
}

func parallelMultiply(A []int, B []int, C []int, size int, procAmount int) {
	numRows := size
	jobs := make(chan int, numRows)
	var wg sync.WaitGroup
	wg.Add(procAmount)

	for proc := 0; proc < procAmount; proc++ {
		go thread(A, B, C, size, jobs, &wg)
	}

	for i := 0; i < size; i++ {
		jobs <- i
	}
	close(jobs)

	wg.Wait()
}

func linearMultiply(A []int, B []int, C []int, size int) {
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			tmp := 0

			for k := 0; k < size; k++ {
				tmp += A[i * size + k] * B[j * size + k]
			}

			C[i * size + j] = tmp
		}
	}
}

func main() {
	sizes := [] int { 100, 1000, 5000 }
	procAmount := 4

	for _, size := range sizes {
		A := make([]int, size * size)
		B := make([]int, size * size)
		C := make([]int, size * size)

		initRandom(A, size)
		initRandom(B, size)

		transpose(B, size)

		start := time.Now()

		parallelMultiply(A, B, C, size, procAmount)

		elapsed := time.Since(start)

		fmt.Printf("Time for parallel size %d = %s\n", size, elapsed)

		start = time.Now()

		linearMultiply(A, B, C, size)

		elapsed = time.Since(start)

		fmt.Printf("Time for linear size %d = %s\n", size, elapsed)
	}
}