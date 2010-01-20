import qualified Data.Map as M
import qualified Data.Set as S
type Network = M.Map Int (S.Set Int)

initial :: Network
initial = M.fromList . map (\(a,xs) -> (a,S.fromList xs)) $ [
	(1,[6]),
	(2,[6]),
	(3,[4,5,6,15]),
	(4,[3,5,6]),
	(5,[3,4,6]),
	(6,[1,2,3,4,5,7]),
	(7,[6,8]),
	(8,[7,9]),
	(9,[10,12]),
	(10,[9,11]),
	(11,[10,12]),
	(12,[9,11,13]),
	(13,[12,14,15]),
	(14,[13]),
	(15,[3,13]),
	(16,[17,18]),
	(17,[16,18]),
	(18,[16,17]) ]

addfriend :: Network -> Int -> Int -> Network
addfriend mm a b = addfriend' (addfriend' mm a b) b a where
	addfriend' mm' a' b' = M.alter f a' mm' where
		f (Just ss) = Just $ S.insert b' ss
		f Nothing = Just $ S.singleton b'

removefriend :: Network -> Int -> Int -> Network
removefriend mm a b = removefriend' (removefriend' mm a b) b a where
	removefriend' mm' a' b' = M.alter f a' mm' where
		f (Just ss) = Just $ S.delete b' ss

fromJust a = let Just k = a in k

friends :: Network -> Int -> Int
friends mm k = let Just a = M.lookup k mm in S.size a

friends2 :: Network -> Int -> Int
friends2 mm k = let fr k' = fromJust $ M.lookup k' mm; (\\) = S.difference
	in S.size $ (S.unions . S.elems . S.map fr $ fr k) \\ (fr k) \\ (S.singleton k)

degrees :: Network -> Int -> Int -> Maybe Int
degrees mm a b = let 
	fr k = fromJust $ M.lookup k mm
	next (l,xs) = (l' ,S.filter (na l') . S.unions . S.elems $ S.map fr xs) where
		l' = l ++ S.elems xs
	na l x = not $ x `elem` l
	fn xs = let a = filter ((b `elem`) . snd) xs in if null a then Nothing else Just (fst . head $ a)
	in fn . zip [0..] . takeWhile (not . null) . map (S.elems . snd) . iterate next $ ([],S.singleton a)

run = run' [[]] initial . lines where
	run' o m ("i":a:b:xs) = run' o (addfriend m (read a) (read b)) xs
	run' o m ("d":a:b:xs) = run' o (removefriend m (read a) (read b)) xs
	run' o m ("n":a:xs) = run' (o++[show (friends m (read a))]) m xs
	run' o m ("f":a:xs) = run' (o++[show (friends2 m (read a))]) m xs
	run' o m ("s":a:b:xs) = let r = (degrees m (read a) (read b)) in run' (o++[case r of
		Just k -> show k; Nothing -> "Not connected" ]) m xs
	run' o _ ("q":_) = o

main = interact (unlines . run)
