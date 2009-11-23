package automenta.spacenet.os.view;

import automenta.spacenet.ID;
import automenta.spacenet.space.object.widget.icon.WideIcon;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.data.ListRect;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.plugin.file.VirtualFile;
import automenta.spacenet.var.number.IntegerVar;

public class FileDirectoryView implements ObjectView<VirtualFile> {

	@Override public String getName(VirtualFile i) {
		return "Files in " + i.getFile().getAbsolutePath();
	}

	@Override public double getStrength(VirtualFile i) {
		if (i.isDirectory())
			return 1.0;
		return 0.0;
	}

	@Override public void run(VirtualFile i, ObjectVar<Space> o) throws Exception {
		Rect r = new Rect();

		i.updateDepth(1);
		r.add(new ListRect<ID>(i, new ArrangeColumn(0.01, 0.01), new IntegerVar(10), new RectBuilder<ID>() {
			@Override public Rect newRect(ID y) {
				Rect r = new Rect();
				r.add(new WideIcon(y));
				return r;
			}			
		}));
		
		o.set(r);		
	}

}
