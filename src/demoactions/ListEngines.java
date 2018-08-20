
/**
http://www.java2s.com/Tutorial/Java/0120__Development/Listthescriptengines.htm
https://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
*/

package demoactions;

import java.util.*;
import javax.script.*;

import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.Buffer;

import org.gjt.sp.util.Log;

public class ListEngines {
	// See D:\works-plugins-win\DemoButtonsAB\[sandbox]\OnThreads.java
	// for a standalone Java program.

	private View currentView = jEdit.getActiveView();
	private Buffer newBuffer = jEdit.newFile(currentView);

	private static StringBuilder sbt;  //total

	public ListEngines() {
		sbt = new StringBuilder();
	}

	/**
	https://docs.oracle.com/javase/7/docs/api/javax/script/ScriptEngineManager.html
	== Constructor ==

	ScriptEngineManager()
		If the thread context ClassLoader can be accessed by the caller, then the
		effect of calling this constructor is the same as calling
		ScriptEngineManager(Thread.currentThread().getContextClassLoader()).
	ScriptEngineManager(ClassLoader loader)
		This constructor loads the implementations of ScriptEngineFactory
		visible to the given ClassLoader using the service provider mechanism.

		If loader is null, the script engine factories that are bundled
		with the platform and that are in the usual extension directories
		(installed extensions) are loaded.
	*/

	/**
	http://www.theserverside.com/tutorial/Classloaders-Demystified-Understanding-How-Java-Classes-Get-Loaded-in-Web-Applications

	== What does a classloader do? ==
	If a Java class is invoked and needs to be executed on a Java Virtual Machine,
	a special Java component, called a classloader, is used to find the Java class
	of interest, pull that Java class off of the file system, and execute
	the bytecode of that class file on the Java Virtual Machine.

	A classloader is a special Java class file that is responsible for loading other
	classes onto a Java Virtual Machine. While this may seem simple and straightforward,
	don't get too cocky, ‘cuz from here, things get really complicated.

	Life would be simple if there was just one, monolithic classloader that did all
	of the classloading, but life just isn't that simple. There are actually quite
	a number of different classloaders that work together to locate and load Java
	bytecode. If you want to package your applications properly, and avoid
	ClassNotFoundExceptions, you must be familiar with
	the many WebSphere classloaders.

	If Java classes are loaded by classloaders, and classloaders are Java components,
	who loads the first classloader?

	The old chicken and egg scenario, rearing its ugly head. If classloaders are Java
	classes, and Java classes must be loaded onto a JVM to run, how does the first
	classloader get loaded? It's a good question.

	When a JVM starts up, a special chunk of machine code runs that loads
	the system classloader. This special hunk of machine code is known
	as the null classloader.

	== The Null Classloader ==
	The machine code that initializes the system classloader is referred to as
	the null classloader because it is not a Java class at all, as are all other
	classloaders. The null classloader is platform specific machine instructions
	that kick off the whole classloading process.

	All classloaders, with the exception of the null classloader, are implemented
	as Java classes. Something must load the very first Java classloader to get
	the process started. Loading the first pure Java classloader is the
	job of the null classloader.

	The null classloader also takes care of loading all of the code needed to support
	the basic Java Runtime Environment (JRE), including classes in
	the java.util and the java.lang packages.

	== The System Classloader: sun.misc.Launcher ==
	The null classloader loads a true Java class of type sun.misc.Launcher.
	The sun.misc.Launcher classloader prepares the basic Java Runtime Environment
	needed by our application server.

	Truth be told, the sun.misc.Launcher is actually made up of two inner
	classes that do the actual classloading:
	* sun.misc.Launcher$ExtClassLoader (parent)
	* sun.misc.Launcher$AppClassLoader (system)

	ExtClassLoader is the parent of AppClassLoader. Of the two, the inner class,
	sun.misc.Launcher$AppClasLoader holds the true title of system classloader

	Quite appropriately, the sun.misc.Launcher is often referred to as
	the bootstrap classloader as well as the system classloader. Any jars found
	in the jre/lib directory of the JRE (not the WebSphere lib directory),
	will be loaded by the system classloader. The system classloader is tightly
	integrated with the components that implement the Java Runtime Environment (JRE).
	*/

	// List all threads and recursively list all subgroup

	public static void listThreads(ThreadGroup group, String indent) {

		ClassLoader cll;
		ScriptEngineManager mngr;
		List<ScriptEngineFactory> sefact;
		StringBuilder sb;

		sbt.append(indent + "Group[" + group.getName() + ":"
			+ group.getClass() + "]\n");
		int nt = group.activeCount();
		Thread[] threads = new Thread[nt * 2 + 10]; //nt is not accurate
		nt = group.enumerate(threads, false);

		// List every thread in the group
		for (int i = 0; i < nt; i++) {
			Thread t = threads[i];
			sbt.append(indent + "  Thread[" + t.getName() + ":"
				+ t.getClass() + "]\n");

			cll = t.getContextClassLoader();
			mngr = new ScriptEngineManager(cll);
			sefact = mngr.getEngineFactories();

			sb = new StringBuilder();
			// http://www.java2s.com/Tutorial/Java/0120__Development/Listthescriptengines.htm
			for (ScriptEngineFactory fct : sefact) {
				sb.append(indent + "  " + "  " + "factory:    " + fct.toString() + "\n");
				sb.append(indent + "  " + "  " + "engineName: " + fct.getEngineName() + "\n");
				sb.append(indent + "  " + "  " + "version:    " + fct.getEngineVersion() + "\n");
				sb.append(indent + "  " + "  " + "language:   " + fct.getLanguageName() + "\n");
				sb.append(indent + "  " + "  " + "lang. ver.: " + fct.getLanguageVersion() + "\n");
				sb.append(indent + "  " + "  " + "extensions: " + fct.getExtensions() + "\n");
				sb.append(indent + "  " + "  " + "mime types: " + fct.getMimeTypes() + "\n");
				sb.append(indent + "  " + "  " + "names:      " + fct.getNames() + "\n\n");
			}
			sbt.append(indent + "  ClassLoader: " + cll + "\n");
			sbt.append(indent + "  Script. manager: " + mngr + "\n");
			sbt.append(indent + "  Script. engines:\n" + sb.toString());
		}

		// Recursively list all subgroups
		int ng = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[ng * 2 + 10];
		ng = group.enumerate(groups, false);

		for (int i = 0; i < ng; i++) {
			listThreads(groups[i], indent + "  ");
		}
	}

	//public String doThreadEngineList() {
	public void doThreadEngineList() {
		// Walk up all the way to the root thread group
		ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup parent;
		while ((parent = rootGroup.getParent()) != null) {
			rootGroup = parent;
		}
		listThreads(rootGroup, "");
		//
		/*
		// https://blogs.oracle.com/sundararajan/understanding-java-class-loading
		== bootstrap loader ==
		...
		sun.misc.Launcher$AppClassLoader@...
		== application class loader ==
		(1) Loads classes from application classpath
		(2) Application classpath is set using ...
		...
		(4) java.lang.ClassLoader.getSystemClassLoader()
		returns this loader
		...
		*/
		StringBuilder sb = new StringBuilder();
		ClassLoader appClassLoader = java.lang.ClassLoader.getSystemClassLoader();
		ScriptEngineManager mngrAppClassLoader = new ScriptEngineManager(appClassLoader);
		List<ScriptEngineFactory> sef = mngrAppClassLoader.getEngineFactories();
		sb = new StringBuilder();
		for (ScriptEngineFactory fct : sef) {
			sb.append("  " + "  " + "factory:    " + fct.toString() + "\n");
			sb.append("  " + "  " + "engineName: " + fct.getEngineName() + "\n");
			sb.append("  " + "  " + "version:    " + fct.getEngineVersion() + "\n");
			sb.append("  " + "  " + "language:   " + fct.getLanguageName() + "\n");
			sb.append("  " + "  " + "lang. ver.: " + fct.getLanguageVersion() + "\n");
			sb.append("  " + "  " + "extensions: " + fct.getExtensions() + "\n");
			sb.append("  " + "  " + "mime types: " + fct.getMimeTypes() + "\n");
			sb.append("  " + "  " + "names:      " + fct.getNames() + "\n\n");
		}
		sbt.append("  =================================  \n");
		sbt.append("  ClassLoader: " + appClassLoader + "\n");
		sbt.append("  Script. manager: " + mngrAppClassLoader + "\n");
		sbt.append("  Script. engines:\n" + sb.toString());
		//
		newBuffer.insert(0, sbt.toString());
		//return sbt.toString();
	}

	// main:
	/*
	public static void main(String args[]) {
		OnThreads ot = new OnThreads();
		String ss = ot.doThreadEngineList();
		System.out.println(ss);
	}
	*/
}