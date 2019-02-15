/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

// Delete resources before test
def clientId = System.getenv("CLIENT_ID")
def tenantId = System.getenv("TENANT_ID")
def key = System.getenv("KEY")
def command = """
    az login --service-principal -u ${clientId}  -p ${key} --tenant ${tenantId}
    az group delete -y -n maven-functions-it-rg-2
    az logout
"""
def process = ["bash", "-c", command].execute()
println process.text
