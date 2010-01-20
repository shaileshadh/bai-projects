import Char
import NumberTheory.Sieve.ONeill
happy n = happy' $ iterate step n where
	step x = sum . map ((^2) . fromIntegral . digitToInt) $ show x
	happy' (1:_) = True
	happy' (4:_) = False
	happy' (_:xs) = happy' xs
run = sum . take 30000 . filter happy $ primes
