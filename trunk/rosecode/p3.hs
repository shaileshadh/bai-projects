import NumberTheory.Sieve.ONeill
mirrors = filter (\s -> let s' = show s in s' == reverse s') $ primes
run = sum . takeWhile (<10^6) $ mirrors
