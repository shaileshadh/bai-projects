import Char
prime n = n>1 && prime' where
	prime' = all (\x -> n `mod` x /= 0) . takeWhile (\x -> x*x<=n) $ [2..]
perms :: (Int,Int) -> [Int]
perms pr = map read . ps $ pr where
	ps (a,b) = map (concatMap show) [ [a], [b], [a,b], [b,a] ]
concatable pr = all prime $ perms pr
run = length . filter concatable $ [ (a,b) | a<-[1..999], b<-[1..999] , a<=b ]
main = print run
