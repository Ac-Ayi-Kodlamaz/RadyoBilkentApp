package Listeners;

public class OpenInBrowserListener extends OnClickListener {
    
    private String url;

    public OpenInBrowserListener(){
        System.out.println("URL should be provided");
    }
    
    public OpenInBrowserListener(String url){
        this.url = url;
    }
    
    @Override
    public void onClick(View v) {
        if (url.startsWith("https://") || url.startsWith("http://")) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            System.out.println("Invalid URL");
        }
    }
}
