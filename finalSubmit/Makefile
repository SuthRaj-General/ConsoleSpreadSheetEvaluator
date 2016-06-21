JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	mainSSE.java \
	LoadSpreadSheet.java \
	ColumnMatch.java \
	ForwardReference.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
