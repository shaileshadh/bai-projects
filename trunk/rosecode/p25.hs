import NumberTheory.Sieve.ONeill
rows = rows' primes 1 where
	rows' xs n = take n xs : rows' (drop n xs) (n+1)
run = sum $ rows !! 999
