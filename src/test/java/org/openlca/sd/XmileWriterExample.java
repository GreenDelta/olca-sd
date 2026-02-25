package org.openlca.sd;

import java.io.File;

import org.openlca.sd.model.SdModel;
import org.openlca.sd.model.XmileWriter;
import org.openlca.sd.xmile.Xmile;

public class XmileWriterExample {

	public static void main(String[] args) {
		var xmile = Xmile.readFrom(new File("examples/plastic-subs.stmx"))
				.orElseThrow();
		var model = SdModel.readFrom(xmile).orElseThrow();
		XmileWriter.write(model, new File("examples/roundtrip.xml"))
			.orElseThrow();
	}

}
