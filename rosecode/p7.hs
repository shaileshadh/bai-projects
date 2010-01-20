import Char
tobase 0 _ = []
tobase x base = let (a,b) = quotRem x base in b : tobase a base
eqal n = let
	h = tobase n 8
	j = flip tobase 9 . read . reverse . show $ n
	in h == j
-- Found the limit to be much less than 10 million, by trial and error.
run = map intToDigit . reverse . flip tobase 8 . head $ filter eqal [10000..]
