from setuptools import setup, find_packages

setup(
    name="vectorex-client",
    version="1.0.0",
    description="Python client for VectoRex vector search engine",
    author="Your Name",
    author_email="your.email@example.com",
    packages=find_packages(),
    install_requires=[
        "requests>=2.25.1",
        "pydantic>=1.8.2"
    ],
    python_requires=">=3.7",
)