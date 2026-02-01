import urllib.request
import os

images = {
    "images/hero.jpg": "https://images.unsplash.com/photo-1558981806-ec527fa84c3d",
    "images/gallery2.jpg": "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?q=80&w=800&auto=format&fit=crop", 
    "images/gallery3.jpg": "https://images.unsplash.com/photo-1449426468159-d96dbf08f19f?q=80&w=800&auto=format&fit=crop"
}

for path, url in images.items():
    try:
        urllib.request.urlretrieve(url, path)
        print(f"Downloaded {path}")
    except Exception as e:
        print(f"Error {path}: {e}")
