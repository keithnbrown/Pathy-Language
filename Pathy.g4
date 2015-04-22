grammar Pathy;

r	:	(ds|fs)+ DEND (qs|fs)* ;

ds	:	op=(NODE|JUNCT|ACT) idpar PSTART PEND ';'					#SimpleDec
	|	ENT idpar PSTART idpar PEND ';'							#EntDec
	|	LINK idpar PSTART idpar ',' idpar PEND ';'					#LinkDecDef
	|	LINK idpar PSTART idpar ',' idpar ',' intpar PEND ';'				#LinkDecWeight
	|	LINK idpar PSTART idpar ',' idpar ',' intpar ',' dirpar PEND ';'		#LinkDecBoth
	|	'AssignAction' PSTART idpar ',' idpar PEND ';'					#AssignAct
	|	'DeleteItem' PSTART idpar PEND ';'								#DelItem
	;

fs	:	op=(SL2|SLB) PSTART ID PEND ';'					#SetLink2B
	|	op=(SE|SW) PSTART ID ',' INT PEND ';'				#SetVals
	|	'MoveEntity' PSTART ID PEND ';'					#MoveEnt
	|	'SetLinkOneWay' PSTART ID ',' ID ',' ID PEND ';'		#SetLink1W
	|	'SetJunctDirection' PSTART ID ',' ID ',' ENUMDIR PEND ';'	#SetJunctDir
	;

qs	:	op=(FPN|FPL|FPJ|FPA|FPE) PSTART PEND ';'		 	#NoParamQuery
	|	op=(F1L|F1D|F1W|F1A|F1C|F1I|F1T|F1B) PSTART ID PEND ';'		#OneParamQuery
	|	op=(F2A|F2B|F2C|F2D|F2E) PSTART ID ',' ID PEND ';'		#TwoParamQuery
	;

idpar	:	ID		#idparam ;
intpar	:	INT		#intparam ;
dirpar	:	ENUMDIR		#dirparam ;
boolpar	:	BOOL		#boolparam ;

SE	:	'SetEnergy';
SW	:	'SetWeight';
SL2	:	'SetLinkTwoWay';
SLB	:	'SetLinkBlocked';

FPN	:	'PrintNodes';
FPL	:	'PrintLinks';
FPJ	:	'PrintJunctions';
FPA	:	'PrintActions';
FPE	:	'PrintEntities';

F1L	:	'Links';
F1D	:	'Direction';
F1W	:	'Weight';
F1A	:	'ActionsThere';
F1C	:	'Connectivity';
F1I	:	'WhereIs';
F1T	:	'LinkedTo';
F1B	:	'ConnectedBy';

F2A	:	'ConnectedBy';
F2B	:	'CanMove';
F2C	:	'CanMoveWhy';
F2D	:	'EnergyReq';
F2E	:	'SharedActions';

DEND	:	'DecEnd;' ;

ID	:	[A-Za-z] [A-Za-z0-9]* ;
INT	:	[0-9]+ ;
ENUMDIR	:	[0-3] ;
BOOL	:	'true' | 'false' ;

PSTART	:	'(' ;
PEND	:	')' ;

NODE	:	'@' ;
LINK	:	'^' ;
JUNCT	:	'%' ;
ACT	:	'$' ;
ENT	:	'£' ;

WS	:	[ \t\r\n]+ -> skip ;
