import NumberTheory.Sieve.ONeill
import Data.List
ps = let pm = takeWhile (<10^6) primes
	in filter (\(a,b) -> b-a==2) $ zip pm (tail pm)
run = (+2) . maximum $ zipWith (\(a,b) (c,d) -> c-b) ps (tail ps)
