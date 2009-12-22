
% ozc -x hello.oz
% ./hello

functor
import
	System
	Application
define
	{System.showInfo "Hello world!"}
	{Application.exit 0}
end
