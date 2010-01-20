import Data.List
import Char
pandigital xs = length xs == 9 && sort xs == [1..9]
run = sum . map fst . filter (pandigital . map digitToInt . show . snd) . zip [1..] . take (10^6) $
	iterate (\x -> let s = show (3*x) in if length s < 9 then r s else r $ drop (length s - 9) s) 3
	where r x = read x :: Integer
