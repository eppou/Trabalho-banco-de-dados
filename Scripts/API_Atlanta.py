#!/usr/bin/env python

# make sure to install these packages before running:
# pip install pandas
# pip install sodapy

import pandas as pd
from sodapy import Socrata

# Unauthenticated client only works with public data sets. Note 'None'
# in place of application token, and no username or password:
#client = Socrata("performance.fultoncountyga.gov", None)

# Example authenticated client (needed for non-public datasets):
client = Socrata("performance.fultoncountyga.gov",
                 "5ouzUcOq6Imk1lWH5FRRzCekO",
                 username="vinipre7@gmail.com",
                 password="Naruto100")

# First 2000 results, returned as JSON from API / converted to Python list of
# dictionaries by sodapy.
results = client.get("jgdb-bp9a", limit=2000)

# Convert to pandas DataFrame
results_df = pd.DataFrame.from_records(results)
results_df.to_json('dadosAtlanta_example.json', orient='records', lines=True)
