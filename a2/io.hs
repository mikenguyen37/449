import System.IO
import System.Environment
import System.Exit
import Data.Char
import Data.List


main = do args <- getArgs
          handle <- openFile (args !! 0) ReadMode
          contents <- (hGetContents handle)

          -- removes '\r' characters, splits the string by lines and removes end-of-line whitespace
          let inAll = map noEndSpaces (lines (noCR contents))
	  -- split into sections
              inName = fromTo "" "forced partial assignment:" inAll
	      inForced = fromTo "forced partial assignment:" "forbidden machine:" inAll
	      inForbidden = fromTo "forbidden machine:" "too-near tasks:" inAll
	      inTooNearTask = fromTo "too-near tasks:" "machine penalties:" inAll
	      inMachPen = fromTo "machine penalties:" "too-near penalities" inAll
	      inTooNearPen = fromTo "too-near penalities" "" inAll

	  -- check that all sections exist (should have at least a whitespace line)
	  if (elem 0 (map length [inName,inForced,inForbidden,inTooNearTask,inMachPen,inTooNearPen]))
	  then do putStrLn "Error while parsing file (empty section)"
                  exitSuccess
	  else return ()

	  -- error check inName
          if ((length (noWhitespaceLines inName) /= 2) || (((noWhitespaceLines inName) !! 0) /= "Name:"))
	  then do putStrLn "Error while parsing file (Name:)"
                  exitSuccess
	  else return ()

	  -- ERROR CHECK AND PARSE FORCED
	  -- check pair syntax (x,y)
	  if (not (andList (map isPair (noWhitespaceLines inForced))))
	  then do putStrLn "Error while parsing file (not pair - inForced)"
                  exitSuccess
	  else return ()

	  -- check (mach,task) syntax
	  if (not (andList (map isMT (noWhitespaceLines inForced))))
	  then do putStrLn "invalid machine/task (inForced)"
                  exitSuccess
	  else return ()

	  -- parse [(mach,task),...] to [(int,int),...]
	  let forced = parseMT (noWhitespaceLines inForced)

	  -- look for duplicates
	  if ((hasDupes(map fst forced)) || (hasDupes(map snd forced)))
	  then do putStrLn "partial assignment error (forced)"
                  exitSuccess
	  else return ()

	  -- ERROR CHECK AND PARSE FORBIDDEN
	  -- check pair syntax (x,y)
	  if (not (andList (map isPair (noWhitespaceLines inForbidden))))
	  then do putStrLn "Error while parsing file (not pair - inForbidden)"
                  exitSuccess
	  else return ()

	  -- check (mach,task) syntax
	  if (not (andList (map isMT (noWhitespaceLines inForbidden))))
	  then do putStrLn "invalid machine/task (inForbidden)"
                  exitSuccess
	  else return ()

	  -- parse [(mach,task),...] to [(int,int),...]
	  let forbidden = parseMT (noWhitespaceLines inForbidden)

	  -- ERROR CHECK AND PARSE TOONEARTASK
	  -- check pair syntax (x,y)
	  if (not (andList (map isPair (noWhitespaceLines inForbidden))))
	  then do putStrLn "Error while parsing file (not pair - inTooNearTask)"
                  exitSuccess
	  else return ()

	  -- check (task,task) syntax
	  if (not (andList (map isTT (noWhitespaceLines inTooNearTask))))
	  then do putStrLn "invalid machine/task (inTooNearTask)"
                  exitSuccess
	  else return ()

	  -- parse [(task,task),...] to [(int,int),...]
	  let tooNearTask = parseTT (noWhitespaceLines inTooNearTask)

	  -- ERROR CHECK AND PARSE MACHPEN
	  -- count non-blank lines
	  if ((length (noWhitespaceLines inMachPen)) /= 8)
	  then do putStrLn "machine penalty error (not 8 lines)"
                  exitSuccess
	  else return ()

	  -- split lines by spaces and parse [[String]] to [[Int]]
	  -- (map words (noWhitespaceLines inMachPen)) splits each line by spaces
	  -- (map read) (map words (noWhitespaceLines inMachPen)) converts each lines Strings into Ints
	  -- map (map read) (map words (noWhitespaceLines inMachPen)) :: [[Int]] converts the whole 2D array
	  let machPen = map (map read) (map words (noWhitespaceLines inMachPen)) :: [[Int]]

	  -- check for 64 elements
	  if (sum (map length machPen)) /= 64
	  then do putStrLn "machine penalty error (not 8 elements each)"
		  exitSuccess
	  else return ()

	  -- ERROR CHECK AND PARSE TOONEARPEN
	  -- check triple syntax (x,y,z)
	  if (not (andList (map isTriple (noWhitespaceLines inTooNearPen))))
	  then do putStrLn "Error while parsing file (not triple - inTooNearPen)"
                  exitSuccess
	  else return ()

	  -- check (task,task,x) syntax
	  if (not (andList (map isTTx (noWhitespaceLines inTooNearPen))))
	  then do putStrLn "invalid task (inTooNearPen)"
                  exitSuccess
	  else return ()

	  let tooNearPen = map parseTTx (map stripBrackets (noWhitespaceLines inTooNearPen))







	  putStr "forced = "
	  putStrLn (show forced)

	  putStr "forbidden = "
	  putStrLn (show forbidden)

	  putStr "tooNearTask = "
	  putStrLn (show tooNearTask)

	  putStrLn "machPen = "
	  putStrLn (show machPen)

	  putStrLn "tooNearPen = "
	  putStrLn (show tooNearPen)

          hClose handle

-- PARSING HELPER FUNCTIONS
-- deletes carriage return characters from a string
noCR :: String -> String
noCR (x:xs)	| x == '\r'	= noCR xs
		| otherwise	= x: noCR xs
noCR []		= []

-- deletes whitespace strings from a list of strings
noWhitespaceLines :: [String] -> [String]
noWhitespaceLines []	= []
noWhitespaceLines (x:xs)	| noEndSpaces x	== ""	= noWhitespaceLines xs
				| otherwise		= x:(noWhitespaceLines xs)

-- deletes all whitespace from the end of a string
noEndSpaces :: String -> String
noEndSpaces []	= []
noEndSpaces (x)	| last x == ' '		= noEndSpaces (init x)
		| last x == '\t'	= noEndSpaces (init x)
		| otherwise		= x

-- parses a [(mach,task),...] into an [(int,int),...]
parseMT :: [String] -> [(Int,Int)]
parseMT []	= []
parseMT (('(':a:',':b:')':[]):xs)	= ((machInt a,taskInt b):(parseMT xs))
parseMT x	= []

-- parses a [(task,task),...] into an [(int,int),...]
parseTT :: [String] -> [(Int,Int)]
parseTT []	= []
parseTT (('(':a:',':b:')':[]):xs)	= ((taskInt a,taskInt b):(parseTT xs))
parseTT x	= []

-- parses a string into an (int,int,int) triple
parseTTx :: String -> (Int,Int,Int)
parseTTx (a:',':b:',':xs)	= (taskInt a,taskInt b,(read xs :: Int))

-- strips the brackets off of the head and last of a string
stripBrackets :: String -> String
stripBrackets []	= []
stripBrackets (x:xs)	| x == '(', (last xs) == ')'	= init xs
			| otherwise			= x:xs

-- returns true if a list has duplicate values
hasDupes :: (Eq a) => [a] -> Bool
hasDupes []	= False
hasDupes (x:xs)	| (elem x xs)	= True
		| otherwise	= hasDupes xs

-- verifies a valid machine
isTask :: Char -> Bool
isTask a	| toUpper a == 'A'	= True
		| toUpper a == 'B'	= True
		| toUpper a == 'C'	= True
		| toUpper a == 'D'	= True
		| toUpper a == 'E'	= True
		| toUpper a == 'F'	= True
		| toUpper a == 'G'	= True
		| toUpper a == 'H'	= True
		| otherwise		= False

-- converts a valid machine to an int (for easier array indexing later)
taskInt :: Char -> Int
taskInt a	| toUpper a == 'A'	= 0
		| toUpper a == 'B'	= 1
		| toUpper a == 'C'	= 2
		| toUpper a == 'D'	= 3
		| toUpper a == 'E'	= 4
		| toUpper a == 'F'	= 5
		| toUpper a == 'G'	= 6
		| toUpper a == 'H'	= 7

-- verifies a valid task
isMach :: Char -> Bool
isMach a	| a == '1'	= True
		| a == '2'	= True
		| a == '3'	= True
		| a == '4'	= True
		| a == '5'	= True
		| a == '6'	= True
		| a == '7'	= True
		| a == '8'	= True
		| otherwise	= False

-- converts a valid task to an int (for easier array indexing later)
machInt :: Char -> Int
machInt a	| a == '1'	= 0
		| a == '2'	= 1
		| a == '3'	= 2
		| a == '4'	= 3
		| a == '5'	= 4
		| a == '6'	= 5
		| a == '7'	= 6
		| a == '8'	= 7

-- verifies a string is a valid character pair
isPair :: String -> Bool
isPair []	= False
isPair (x)	| ((head x) == '(') && ((last x) == ')') && ((countCommas x) == 1)	= True
		| otherwise		= False

-- verifies a string is a valid character triple
isTriple :: String -> Bool
isTriple []	= False
isTriple (x)	| ((head x) == '(') && ((last x) == ')') && ((countCommas x) == 2)	= True
		| otherwise		= False

-- counts the commas in a string
countCommas :: String -> Int
countCommas []	= 0
countCommas (x:xs)	| (x == ',')	= 1 + (countCommas xs)
			| otherwise	= 0 + (countCommas xs)

-- verifies a string is a valid (mach,task) pair
isMT :: String -> Bool
isMT []	= False
isMT ('(':a:',':b:')':[])	| isMach a, isTask b	= True
				| otherwise		= False
isMT (x)	= False

-- verifies a string is a valid (task,task) pair
isTT :: String -> Bool
isTT []	= False
isTT ('(':a:',':b:')':[])	| isTask a, isTask b	= True
				| otherwise		= False
isTT (x)	= False

-- verifies a string is a valid (task,task,x) triple
isTTx :: String -> Bool
isTTx []	= False
isTTx ('(':a:',':b:',':xs)	| isTask a, isTask b	= True
				| otherwise		= False
isTTx (x)	= False

-- returns the original list of strings from one designated string to another
fromTo :: String -> String -> [String] -> [String]
fromTo _ _ []		= []
fromTo [] x y		= deleteFrom x y
fromTo w [] y		= deleteTo w y
fromTo w x y	= deleteTo w (deleteFrom x y)

-- returns the original list of strings up to (and not including) a designated string
deleteFrom :: String -> [String] -> [String]
deleteFrom _ []	= []
deleteFrom [] x	= x
deleteFrom y (x:xs)	| y /= x	= x:(deleteFrom y xs)
			| otherwise	= []

-- returns the original list of strings starting from (and not including) a designated string
deleteTo :: String -> [String] -> [String]
deleteTo _ []	= []
deleteTo [] x	= x
deleteTo y (x:xs)	| y /= x	= deleteTo y xs
			| otherwise	= xs

-- checks if a list is empty
isEmpty :: [a] -> Bool
isEmpty []	= True
isEmpty x	= False

-- ands a list of boolean values
andList :: [Bool] -> Bool
andList []	= True
andList (x:xs)	= x && (andList xs)

