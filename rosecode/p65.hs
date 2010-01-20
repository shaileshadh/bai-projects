import Data.List
import Data.Ratio

chances :: [Int] -> [Int] -> [Ratio Int]
chances dice1 dice2 = map ((% tsum) . length) . group . sort $ [a+b | a<-dice1, b<-dice2]
	where tsum = length dice1 * length dice2

normaldice = chances [1..6] [1..6]

--Optimization: First number must be 1 or else 2 will not be possible.
--Also a<=b<=c<=d.
sixsideddices = [ [1,a,b,c,d,e] | a<-[1..8], b<-[a..8], c<-[b..8], d<-[c..8], e<-[d..8] ]

run = filter ((==normaldice) . uncurry chances) $ [ (a,b) | a<-sixsideddices, b<-sixsideddices]
