--This is essentially the 8-queens problem on a 6x6 board.
--Stealing code from p51 in cstutor.
nqueens n = foldl putq [[]] [1..n] where
	putq pos y = [((x,y):ys) | ys<-pos, x<-[1..n], all (safe (x,y)) ys]
	safe (x1,y1) (x2,y2) = x1/=x2 && y1/=y2 && abs (x1-x2) /= abs (y1-y2)
run = length $ nqueens 6
