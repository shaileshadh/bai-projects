import Data.List
nums = [1..5]
nextrow board = filter f $ permutations nums where
	bcols = transpose board
	f row = not . or $ zipWith elem row bcols
run = length . (!!4) $ iterate (concatMap (\b -> map (\r -> b ++ [r]) (nextrow b))) [[nums]]
