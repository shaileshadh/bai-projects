import Control.Monad
import Data.List
distribution = map (\a -> (head a, fromIntegral $ length a)) . group . sort . map sum
guy = distribution $ replicateM 8 [1..5]
girl = distribution $ replicateM 6 [1..6]
run = pr $ foldl' (\(x,y) ((a,b),(c,d)) -> if a>c then (x+b*d,y) else (x,y+b*d) )
	(0,0) [(a,b)| a<-guy, b<-girl] where
	pr (a,b) = a / (a+b)
