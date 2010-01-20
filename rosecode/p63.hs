--This is the catalan numbers.
catalan n = (fac (2*n)) `div` (fac (n+1) * fac n) where
	fac x = product [1..x]
run = catalan 25
