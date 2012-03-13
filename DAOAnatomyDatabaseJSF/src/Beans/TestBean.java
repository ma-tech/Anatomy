package Beans;

public class TestBean {

    // Init --------------------------------------------------------------------------------------

    private String hiddenInput;

    // Actions -----------------------------------------------------------------------------------

    public void action() {
        System.out.println("hiddenInput: " + hiddenInput);
    }

    // Getters -----------------------------------------------------------------------------------

    public String getHiddenInput() {
        return hiddenInput;
    }

    // Setters -----------------------------------------------------------------------------------

    public void setHiddenInput(String hiddenInput) {
        this.hiddenInput = hiddenInput;
    }
}
