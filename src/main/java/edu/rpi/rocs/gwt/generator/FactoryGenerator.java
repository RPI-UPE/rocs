package edu.rpi.rocs.gwt.generator;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class FactoryGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		// TODO Auto-generated method stub
		logger.log(TreeLogger.INFO, "Generating source for " + typeName, null);
		TypeOracle typeOracle = context.getTypeOracle();
		
		JClassType clazz = typeOracle.findType(typeName);
		if(clazz == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null);
			throw new UnableToCompleteException();
		}
		
		try {
			//logger.log(TreeLogger.INFO, "Generating source for " + clazz.getQualifiedSourceName(), null);
			
			JClassType reflectableType = typeOracle.getType("edu.rpi.rocs.client.Instantiable");
			SourceWriter sourceWriter = getSourceWriter(clazz, context, logger);
			if(sourceWriter != null) {
				sourceWriter.println("public " + reflectableType.getQualifiedSourceName() + " newInstance(String className) {");
				JClassType[] types = typeOracle.getTypes();
				int count = 0;
				for(int i=0;i<types.length;i++)  {
					if(types[i].isInterface() == null && types[i].isAssignableTo(reflectableType)) {
						if(count == 0) {
							sourceWriter.println("    if(\"" + types[i].getQualifiedSourceName() + "\".equals(className)) {");
							sourceWriter.println("        return new " + types[i].getQualifiedSourceName() + "();");
							sourceWriter.println("    }");
						}
						else {
							sourceWriter.println("    else if(\"" + types[i].getQualifiedSourceName() + "\".equals(className)) {");
							sourceWriter.println("        return new " + types[i].getQualifiedSourceName() + "();");
							sourceWriter.println("    }");
						}
						count++;
					}
				}
				sourceWriter.println("return null;");
				sourceWriter.println("}");
				sourceWriter.commit(logger);
				logger.log(TreeLogger.INFO, "Done Generating source for " + clazz.getName(), null);
				return clazz.getQualifiedSourceName()+"Wrapper";
			}
		}
		catch(NotFoundException e) {
			e.printStackTrace();
		}
		return clazz.getQualifiedSourceName()+"Wrapper";
	}
	
	public SourceWriter getSourceWriter(JClassType classType, GeneratorContext context, TreeLogger logger) {
		String packageName = classType.getPackage().getName();
		String simpleName = classType.getSimpleSourceName() + "Wrapper";
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
		composer.addImplementedInterface("edu.rpi.rocs.client.Factory");
		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if(printWriter == null) {
			return null;
		}
		else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}

}
