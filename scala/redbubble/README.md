# Redbubble Homework Exercise

## To run project
Language: Scala 2.12.1

If you don't have it already, SBT (Simple Build Tool) needs to be installed. Follow the link [SBT](http://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html)

SBT should download the required Scala version for you.

Once installed, navigate to redbubble folder and run
 
    >sbt run
This should create all necessary folders and files from your base directory structure

To run all the tests created for this project, run 

    >sbt test
If you'd like to start from a clean slate again, run the following to delete everything from the Target directory.

    >sbt clean 

The output of this program will create files in:

    ~/redbubble/target/HTML
    ~/redbubble/target/HTML/CameraMake
    ~/redbubble/target/HTML/CameraModel
                
Once you've successfully run the program, in your browser open the following file to start browsing around the output of this program.

    ~/redbubble/target/HTML/index.html

Of course, if you need to change the base directory, everything from /target/ above will be created in your newly specific directory.

The config file for the main program can be updated via /redbubble/src/main/resources/application.conf

The config file for the test program can be updated via redbubble/src/test/resources/test.conf

# Brief
The Redbubble system has many digital images, often taken with a camera. We have exported EXIF data from a selection of these images. This data is available via an API.

Instructions
Your task is to create a set of static HTML files to allow a user to browse the images contained in the API.

The API is available at: [/api/v1/works.xml](/api/v1/works.xml)

Create a batch processor that takes data from the API, and produce a single HTML page (based on this output template), for each camera make, camera model and also an index page.

# Requirements

### 1.0 Index Page

    1.1 Index Page must contain
    1.1.1 Thumbnail images for the first 10 work
    1.1.2 Navigation that allows the user to browse to all camera makes.
    
### 2.0 Camera make HTML

    2.1 Camera make Page must contain
    2.1.1 Thumbnail images for the first 10 work
    2.1.2 Navigation that allows the user to browse to the index page.
    2.1.3 Navigation that allows the user to browse to all camera models of that make
       
### 3.0 Camera Model HTML

    3.1 Camera Model Page must contain
    3.1.1 Thumbnail images of all works for that camera make and model
    3.1.2 Navigation that allows the user to browse to the index page.
    3.1.3 Navigation that allows the user to browse to the camera make.
    
### 4.0 Web Pages
    4.1 All web pages must follow template as defined in http://take-home-test.herokuapp.com/templates/output-template.html

### 5.0 Program 

    5.1 The batch processor should take the API URL as a parameter
    5.2 The batch processor should take the output directory as a parameters

## Assumptions

1. With respect to the API, it looks like not every work has a 'Make'. In these cases, I updated the make to "None" 
so as to minimise data leakage and store all it's associated data.
2. Requirement 1.1.1 just says 'first 10 work' for displaying thumbnails. Since not every work has a make, 
I didn't take that as to be a stipulation that every work displayed on the index page had to have a 'Make' 
associated with it. I just display the first 10 works I find.
3. Same goes for 2.1.1
4. Requirement 3.1.1 can be interpreted as displaying thumbnails for the AND condition (CameraMake && Model), 
i.e. thumbnails for only that Model. Or you could read it as all thumbnails for that camera make, and all 
thumbnails for that camera model. I assumed the former, as displaying all cameraMake thumbnails is taken care of one 
level up in the Camera Make page.
5. The HTML template only calls for 1 'nav' tag between the 'header' tags. In the pages for Camera Make and 
Model, I put 2 'navs', just to break it up a bit for page-readability.
6. It wasn't explicit from the API URL where the thumbnails were to come from. I took the thumbnails from the tag 'urls'
7. Since I took the thumbnail images from 'urls', I also used the image size numbers embedded in the 'url' text 
so as to size my 'img src' tags in my HTML
8. Since 'urls' also displays small/medium/large versions of each file, I added a parameter to my config file: 
redbubble.all_picture_size which allows you to select which size you want to display across all web pages

## Notes
 - All requirements have been met.
