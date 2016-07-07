Flickr Viewer
=============

This app is a simple image viewer for Flickr.

Flickr Viewer supports:
* endless scrolling
* history of latest searches
* runtime Flickr image links cache
* cute icon from [Business Cat](http://knowyourmeme.com/memes/business-cat) meme

Ideas for the future:
- add ripple effects everywhere
- persist scrolling location
- fix tiny issues
- better offline mode handling
- add more tests
- use ButterKnife, if we have more views
- Instrumentation espresso tests may fail. This may happen, if it takes time to load Flickr images from Flickr API. Ideally, this should be rewritten to `Espresso.registerIdlingResources()`/`Espresso.unregisterIdlingResources()`
- feed a cat
