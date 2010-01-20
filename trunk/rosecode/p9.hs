import Char
digits = [2,3,5,7]
tkall 0 = [[]]
tkall n = concatMap (\d -> map (d:) $ tkall (n-1)) digits
tries = tkall 5
correct [a,b,c,d,e] = all (\xs -> all (`elem` digits) xs) ks where
	num1 = read . map intToDigit $ [a,b,c]
	num2 = read . map intToDigit $ [d,e]
	ks = map (map digitToInt . show) [num1*num2, e*num1, d*num1]
run = length . filter correct $ tries
