FROM python:3.7.3-slim-stretch

RUN apt-get update
RUN apt-get install -y python-psycopg2 libpq-dev
RUN apt-get install -y libsm6 libxext6 libxrender-dev
RUN apt-get install -y software-properties-common poppler-utils
RUN pip3 install numpy flask gunicorn
RUN pip3 install pandas --upgrade pip
RUN pip3 install redis requests psycopg2-binary

COPY ./ /usr/local/src/
WORKDIR /usr/local/src/

ENV PYTHONPATH=/usr/local/src/