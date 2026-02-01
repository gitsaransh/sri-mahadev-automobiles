import urllib.request
import os

url = "https://images.unsplash.com/photo-1626847037657-fd3622613ce3?q=80&w=1600&auto=format&fit=crop"
path = "images/hero.jpg"

try:
    urllib.request.urlretrieve(url, path)
    print(f"Downloaded {path}")
except Exception as e:
    print(f"Error {path}: {e}")
