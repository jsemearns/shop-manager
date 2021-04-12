# About
 This Android Studio Project is a requirement of DMIT2054 (Android Development) from NAIT. The project is a basic Shop Manager application
 that contains 2 parts: a CRUD view to manage products and a view to display a grid of cards containing product details.

 The purpose of this project is to implement uploading images from the phone to storing it to the app's database and displaying those images in a view. The project also uses the Flexbot Layout to easily manage the cards in the view.

# Getting Started
  ## Emulator Settings
  ### Setup Emulator Gallery Permission
    1. Go to Settings > Apps > Gallery
    2. Tap Permissions
    3. Enable Storage
    4. Drag and drop images from Desktop to Emulator
    5. Restart Emulator

# Dev Notes
  ## Enable File Upload
    - In the manifest file, include the "READ_EXTERNAL_STORAGE" permission
    - Add the following snippet in your view onCreate, replacing "YourActivity" with your activity classname:
  ```
  if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(YourActivity.this,new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
  ```
  ## Event Listener for File Upload
    1. Implement an ACTION_PICK intent in your button event listener. See [example](https://github.com/jsemearns/shop-manager/blob/master/app/src/main/java/ca/nait/jmearns2/shopmanager/ManageShop.java#L131-L133)
    2. In your activity, override "onActivityResult" method. See [example](https://github.com/jsemearns/shop-manager/blob/master/app/src/main/java/ca/nait/jmearns2/shopmanager/ManageShop.java#L196-L227)
    3. Optional: a method to resize uploaded images to prevent memory overflow. See [example](https://github.com/jsemearns/shop-manager/blob/master/app/src/main/java/ca/nait/jmearns2/shopmanager/ManageShop.java#L229-L256)
  ## Storing images

# Further Development
This project can further be developed to implement a fully-working sales application that would allow the user to select an item from the grid of products and specify a quantity. A summary view can also be implemented to display the list of products ordered.
  
# References
- https://github.com/google/flexbox-layout
- https://developer.android.com/guide/topics/ui/layout/cardview