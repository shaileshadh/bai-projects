import Char
--A113873
nums = (0,1) : (1,1) : zipWith next nums (tail nums) where
	next (_,a) (b,c) = let m = (b+1) `mod` 3 in
		if m == 0 || m == 2 then (b+1,a+c)
		else (b+1,(c*2*b `div` 3)+a)

run = sum . map digitToInt . show . snd $ nums!!1001
