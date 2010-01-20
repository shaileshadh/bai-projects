import Char
fibs = 1 : 1 : zipWith (+) fibs (tail fibs)
run = head . filter ((>100) . dsum . show) $ fibs where
	dsum = sum . map digitToInt
