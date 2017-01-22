# Redbubble Homework Exercise

# Brief
The Redbubble system has many digital images, often taken with a camera. We have exported EXIF data from a selection of these images. This data is available via an API.

Instructions
Your task is to create a set of static HTML files to allow a user to browse the images contained in the API.

The API is available at: [/api/v1/works.xml](/api/v1/works.xml)

Create a batch processor that takes data from the API, and produce a single HTML page (based on this output template), for each camera make, camera model and also an index page.

The index page must contain:

Thumbnail images for the first 10 work;
Navigation that allows the user to browse to all camera makes.
Each camera make HTML page must contain:

Thumbnail images of the first 10 works for that camera make;
Navigation that allows the user to browse to the index page and to all camera models of that make.
Each camera model HTML page must contain:

Thumbnail images of all works for that camera make and model;
Navigation that allows the user to browse to the index page and the camera make.
The batch processor should take the API URL and the output directory as parameters.

The data returned from the API contains a small sample set of works.

## Note

