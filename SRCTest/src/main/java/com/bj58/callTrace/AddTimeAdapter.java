package com.bj58.callTrace;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 参考资料：https://www.cnblogs.com/liuling/archive/2013/05/25/asm.html
 * @ClassName:AddTimeAdapter
 * @Description:
 * @Author lishaoping
 * @Date 2019年2月20日
 * @Version V1.0
 * @Package com.bj58.callTrace
 */
public class AddTimeAdapter extends ClassAdapter{
	private String owner;
    private boolean isInterface;
	public AddTimeAdapter(ClassVisitor arg0) {
		super(arg0);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			             String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		owner = name;
	    isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			            String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if(!name.equals("<init>") && !isInterface && mv!=null){
	         //为方法添加计时功能,,改变方法字节码的操作方法
	         mv = new AddTimeMethodAdapter(mv);
		}
		return mv;
	}
	
	/**
	 * 为父类方法访问的时候，操作的时候
	 */
	@Override
	public void visitEnd() {
		//添加字段
	     if(!isInterface){
	         FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "timer", "J", null, null);
	         if(fv!=null){
	             fv.visitEnd();
	         }
	     }
		cv.visitEnd();
	}
	
	class AddTimeMethodAdapter extends MethodAdapter{

		public AddTimeMethodAdapter(MethodVisitor arg0) {
			super(arg0);
		}

		/**
		 * 开头添加的方式
		 */
		@Override
		public void visitCode() {
			mv.visitCode();
		     mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
		     mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
		     mv.visitInsn(Opcodes.LSUB);
		     mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
		}
		
		/**
		 * 结尾添加的方式
		 */
		@Override
		public void visitInsn(int opcode) {
			if((opcode>=Opcodes.IRETURN && opcode<=Opcodes.RETURN) || opcode==Opcodes.ATHROW){
                 mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
                 mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                 mv.visitInsn(Opcodes.LADD);
                 mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
         	}
			mv.visitInsn(opcode);
		}
		
		@Override
		public void visitMaxs(int maxStack, int maxLocal) {
			super.visitMaxs(maxStack+4, maxLocal);
		}
	}
}
