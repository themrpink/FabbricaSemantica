

public class ErrorPage  extends Page {

	private Div divError;
	private Label label;
	
	public ErrorPage() {
		this.label = new Label();
		this.divError =  new Div(label);
	}
	
	@Override
	public void setDefaultAttributes() {
		super.setDefaultAttributes();
	}
	
	@Override
	public void connectToServer() {
		
	}

	@Override
	public void setStyle() {
		label.textContent("ops, è stato riscontrato un errore nel server.");
		divError.className("shadow-lg p-4 mb-4 bg-white rounded-top text-danger w-50 mx-auto");
		
	}

	@Override
	public void build() {
		super.mainForm.appendTag(divError);
		
	}

}
