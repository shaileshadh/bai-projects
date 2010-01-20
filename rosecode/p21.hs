import Char
binary x = reverse $ binary' x where
	binary' n
		| n < 2 = [n]
		| otherwise = let (d,m) = divMod n 2 in
			m : binary' d

nums = map (read . map intToDigit . binary) [1..] :: [Integer]
run = sum . take 100000 $ filter (\s -> s `mod` 19 == 0) nums
