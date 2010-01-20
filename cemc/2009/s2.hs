import Data.List

xor a b = (a || b) && not (a && b)

run xss = length $ foldl next [head xss] (tail xss) where
	next ps c = nub . concat . map (\p -> [c, zipWith xor c p]) $ ps

parse f = map (map (\x -> if x=="0" then False else True) . words) . drop 2 . lines $ f

main = interact (show . run . parse)
